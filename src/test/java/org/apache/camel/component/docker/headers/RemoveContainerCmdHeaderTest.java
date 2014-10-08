package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.RemoveContainerCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Remove Container Request headers are applied properly
 */
public class RemoveContainerCmdHeaderTest extends BaseDockerHeaderTest<RemoveContainerCmd> {
    
    @Mock
    private RemoveContainerCmd mockObject;
    
    @Test
    public void removeContainerHeaderTest() {
        
        String containerId = "9c09acd48a25";
        boolean force = true;
        boolean removeVolumes = true;
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_CONTAINER_ID, containerId);
        headers.put(DockerConstants.DOCKER_FORCE, force);
        headers.put(DockerConstants.DOCKER_REMOVE_VOLUMES, removeVolumes);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
                
        Mockito.verify(dockerClient,Mockito.times(1)).removeContainerCmd(containerId);
        Mockito.verify(mockObject,Mockito.times(1)).withForce(Mockito.eq(force));
        Mockito.verify(mockObject,Mockito.times(1)).withRemoveVolumes(Mockito.eq(removeVolumes));


        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.removeContainerCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.REMOVE_CONTAINER;
    }

}
