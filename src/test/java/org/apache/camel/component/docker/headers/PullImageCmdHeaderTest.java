package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.PullImageCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Pull Image Request headers are applied properly
 */
public class PullImageCmdHeaderTest extends BaseDockerHeaderTest<PullImageCmd> {
    
    @Mock
    private PullImageCmd mockObject;
    
    @Test
    public void pullImageHeaderTest() {
        
        String repository = "docker/empty";
        String tag = "1.0";
        String registry = "registry";
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_REPOSITORY, repository);
        headers.put(DockerConstants.DOCKER_TAG, tag);
        headers.put(DockerConstants.DOCKER_REGISTRY, registry);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
                
        Mockito.verify(dockerClient,Mockito.times(1)).pullImageCmd(repository);
        Mockito.verify(mockObject,Mockito.times(1)).withTag(Mockito.eq(tag));
        Mockito.verify(mockObject,Mockito.times(1)).withRegistry(Mockito.eq(registry));
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.pullImageCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.PULL_IMAGE;
    }

}
