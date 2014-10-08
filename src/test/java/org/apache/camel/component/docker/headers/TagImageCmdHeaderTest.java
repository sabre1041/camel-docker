package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.TagImageCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Tag Image Request headers are applied properly
 */
public class TagImageCmdHeaderTest extends BaseDockerHeaderTest<TagImageCmd> {
    
    @Mock
    private TagImageCmd mockObject;
    
    @Test
    public void topImageHeaderTest() {
        
        String imageId = "be29975e0098";
        String repository = "docker/empty";
        String tag = "1.0";
        boolean force = true;
        
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_IMAGE_ID, imageId);
        headers.put(DockerConstants.DOCKER_REPOSITORY, repository);
        headers.put(DockerConstants.DOCKER_TAG, tag);
        headers.put(DockerConstants.DOCKER_FORCE, force);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
                
        Mockito.verify(dockerClient,Mockito.times(1)).tagImageCmd(imageId, repository, tag);
        Mockito.verify(mockObject,Mockito.times(1)).withForce();
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.tagImageCmd(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.TAG_IMAGE;
    }

}
