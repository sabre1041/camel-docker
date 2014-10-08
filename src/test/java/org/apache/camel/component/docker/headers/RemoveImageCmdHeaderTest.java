package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.RemoveImageCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Remove Image Request headers are applied properly
 */
public class RemoveImageCmdHeaderTest extends BaseDockerHeaderTest<RemoveImageCmd> {

    @Mock
    private RemoveImageCmd mockObject;
    
    @Test
    public void removeImageHeaderTest() {
        
        String imageId = "be29975e0098";
        Boolean noPrune = false;
        Boolean force = true;
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_IMAGE_ID, imageId);
        headers.put(DockerConstants.DOCKER_NO_PRUNE, noPrune);
        headers.put(DockerConstants.DOCKER_FORCE, force);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
        
        Mockito.verify(dockerClient,Mockito.times(1)).removeImageCmd(imageId);
        Mockito.verify(mockObject,Mockito.times(0)).withNoPrune();
        Mockito.verify(mockObject,Mockito.times(1)).withForce();

        
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.removeImageCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.REMOVE_IMAGE;
    }

}
