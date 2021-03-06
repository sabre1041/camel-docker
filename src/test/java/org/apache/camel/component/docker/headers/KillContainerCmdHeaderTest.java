package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.KillContainerCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Kill Container Request headers are applied properly
 */
public class KillContainerCmdHeaderTest extends BaseDockerHeaderTest<KillContainerCmd> {
    
    @Mock
    private KillContainerCmd mockObject;
    
    @Test
    public void stopContainerHeaderTest() {
        
        String containerId = "9c09acd48a25";
        String signal = "signal";
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_CONTAINER_ID, containerId);
        headers.put(DockerConstants.DOCKER_SIGNAL, signal);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
                
        Mockito.verify(dockerClient,Mockito.times(1)).killContainerCmd(containerId);
        Mockito.verify(mockObject,Mockito.times(1)).withSignal(Mockito.eq(signal));
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.killContainerCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.KILL_CONTAINER;
    }

}
