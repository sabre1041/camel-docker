package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.CreateImageCmd;

import java.io.InputStream;
import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Create Image Request headers are parsed properly
 */
public class CreateImageCmdHeaderTest extends BaseDockerHeaderTest<CreateImageCmd> {
    
    @Mock
    private CreateImageCmd mockObject;
    
    @Test
    public void createImageHeaderTest() {
        
        String repository = "docker/empty";
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_REPOSITORY, repository);
        
        template.sendBodyAndHeaders("direct:in", "",headers);
        
        Mockito.verify(dockerClient,Mockito.times(1)).createImageCmd(Mockito.eq(repository), Mockito.any(InputStream.class));
        

        
    }
    
    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.createImageCmd(Mockito.anyString(), Mockito.any(InputStream.class))).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.CREATE_IMAGE;
    }

}
