package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.WaitContainerCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Wait Container Request headers are applied properly
 */
public class WaitContainerCmdHeaderTest extends BaseDockerHeaderTest<WaitContainerCmd> {
    
    @Mock
    private WaitContainerCmd mockObject;
    
    @Test
    public void waitContainerHeaderTest() {
        
        String containerId = "9c09acd48a25";
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_CONTAINER_ID, containerId);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
                
        Mockito.verify(dockerClient,Mockito.times(1)).waitContainerCmd(containerId);
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.waitContainerCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.WAIT_CONTAINER;
    }

}
