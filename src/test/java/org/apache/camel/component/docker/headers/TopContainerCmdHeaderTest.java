package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.TopContainerCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Top Container Request headers are applied properly
 */
public class TopContainerCmdHeaderTest extends BaseDockerHeaderTest<TopContainerCmd> {
    
    @Mock
    private TopContainerCmd mockObject;
    
    @Test
    public void topContainerHeaderTest() {
        
        String containerId = "9c09acd48a25";
        String psArgs = "aux";
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_CONTAINER_ID, containerId);
        headers.put(DockerConstants.DOCKER_PS_ARGS, psArgs);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
                
        Mockito.verify(dockerClient,Mockito.times(1)).topContainerCmd(containerId);
        Mockito.verify(mockObject,Mockito.times(1)).withPsArgs(Mockito.eq(psArgs));
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.topContainerCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.TOP_CONTAINER;
    }

}
