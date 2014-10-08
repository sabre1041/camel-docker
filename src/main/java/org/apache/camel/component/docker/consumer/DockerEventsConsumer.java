package org.apache.camel.component.docker.consumer;

import com.github.dockerjava.api.command.EventCallback;
import com.github.dockerjava.api.command.EventsCmd;
import com.github.dockerjava.api.model.Event;

import java.util.Date;
import java.util.concurrent.ExecutorService;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.docker.DockerClientFactory;
import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerEndpoint;
import org.apache.camel.component.docker.DockerHelper;
import org.apache.camel.impl.DefaultConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Docker Consumer for streaming events
 */
public class DockerEventsConsumer extends DefaultConsumer implements EventCallback {

    private transient static final Logger LOGGER = LoggerFactory.getLogger(DockerEventsConsumer.class);

    private DockerEndpoint endpoint;
        
    private EventsCmd eventsCmd;
    
    private ExecutorService eventsExecutorService;

    public DockerEventsConsumer(DockerEndpoint endpoint, Processor processor) throws Exception {
        super(endpoint, processor);
        this.endpoint = endpoint;
              
    }

    @Override
    public DockerEndpoint getEndpoint() {
        return (DockerEndpoint)super.getEndpoint();
    }
    

    /**
     * Determine the point in time to begin streaming events
     */
    private long processInitialEvent()  {

        long currentTime = new Date().getTime();
        
        Long initialRange = DockerHelper.getProperty(DockerConstants.DOCKER_INITIAL_RANGE, endpoint.getConfiguration(), null, Long.class);

        if (initialRange != null) {
            currentTime = currentTime - initialRange;
        } 
        
        return currentTime;
        
        
    }

    @Override
    protected void doStart() throws Exception {
        
        eventsCmd = DockerClientFactory.getDockerClient(endpoint.getConfiguration(), null).eventsCmd(this);
        
        eventsCmd.withSince(String.valueOf(processInitialEvent()));
        eventsExecutorService =  eventsCmd.exec();

        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
 
        if(eventsExecutorService != null && !eventsExecutorService.isTerminated()) {
            LOGGER.trace("Stopping Docker events Executor Service");
            
            eventsExecutorService.shutdown();
        }

        super.doStop();
    }
       

    @Override
    public void onEvent(Event event) {
        
        LOGGER.debug("Received Docker Event: " + event);
                
        final Exchange exchange = getEndpoint().createExchange();
        Message message = exchange.getIn();
        message.setBody(event);
        
        try {
            LOGGER.trace("Processing exchange [{}]...", exchange);
            getAsyncProcessor().process(exchange, new AsyncCallback() {
                @Override
                public void done(boolean doneSync) {
                    LOGGER.trace("Done processing exchange [{}]...", exchange);
                }
            });
        } catch (Exception e) {
            exchange.setException(e);
        }
        if (exchange.getException() != null) {
            getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
        }
        
    }

    @Override
    public void onException(Throwable throwable) {
       LOGGER.error("Error Consuming from Docker Events: {}", throwable.getMessage());
        
    }

    @Override
    public void onCompletion(int numEvents) {
        
        LOGGER.debug("Docker events connection completed. Events processed : {}", numEvents);
        
        eventsCmd.withSince(null);
        
        LOGGER.debug("Reestablishing connection with Docker");
        eventsCmd.exec();
        
    }
}
