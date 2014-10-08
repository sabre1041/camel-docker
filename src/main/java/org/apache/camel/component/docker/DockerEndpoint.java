package org.apache.camel.component.docker;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.docker.consumer.DockerEventsConsumer;
import org.apache.camel.component.docker.exception.DockerException;
import org.apache.camel.component.docker.producer.DockerProducer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriEndpoint;

/**
 * Represents a Docker endpoint.
 */
@UriEndpoint(scheme = "docker", consumerClass = DockerEventsConsumer.class)
public class DockerEndpoint extends DefaultEndpoint {

    private DockerConfiguration configuration;

    public DockerEndpoint() {
    }

    public DockerEndpoint(String uri, DockerComponent component, DockerConfiguration configuration) {
        super(uri, component);
        this.configuration = configuration;
    }

    public DockerEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
        DockerOperation operation = configuration.getOperation();
        
        if(operation != null && operation.canProduce()) {
            return new DockerProducer(this);
        }
        else {
            throw new DockerException(operation + " is not a valid producer operation");
        }
    }

    public Consumer createConsumer(Processor processor) throws Exception {

        DockerOperation operation = configuration.getOperation();

        switch (operation) {
            case EVENTS:
                return new DockerEventsConsumer(this, processor);
            default:
                throw new DockerException(operation + " is not a valid consumer operation");
        }

    }
    

    public boolean isSingleton() {
        return true;
    }

    public DockerConfiguration getConfiguration() {
        return configuration;
    }
    
    @Override
    public boolean isLenientProperties() {
        return true;
    }

 
}
