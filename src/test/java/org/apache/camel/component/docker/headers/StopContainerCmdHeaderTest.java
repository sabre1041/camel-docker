package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.StopContainerCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Stop Container Request headers are applied properly
 */
public class StopContainerCmdHeaderTest extends BaseDockerHeaderTest<StopContainerCmd> {
    
    @Mock
    private StopContainerCmd mockObject;
    
    @Test
    public void stopContainerHeaderTest() {
        
        String containerId = "9c09acd48a25";
        int timeout = 50;
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_CONTAINER_ID, containerId);
        headers.put(DockerConstants.DOCKER_TIMEOUT, timeout);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
                
        Mockito.verify(dockerClient,Mockito.times(1)).stopContainerCmd(containerId);
        Mockito.verify(mockObject,Mockito.times(1)).withTimeout(Mockito.eq(timeout));
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.stopContainerCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.STOP_CONTAINER;
    }

}
