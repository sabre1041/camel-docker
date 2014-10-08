package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Create Container Request headers are parsed properly
 */
public class CreateContainerCmdHeaderTest extends BaseDockerHeaderTest<CreateContainerCmd> {

    
    @Mock
    private CreateContainerCmd mockObject;
    
    @Test
    public void createContainerHeaderTest() {
        
        String imageId = "be29975e0098";
        ExposedPort tcp22 = ExposedPort.tcp(22);

        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_IMAGE_ID, imageId);
        headers.put(DockerConstants.DOCKER_EXPOSED_PORTS, tcp22);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
        
        Mockito.verify(dockerClient,Mockito.times(1)).createContainerCmd(imageId);
        Mockito.verify(mockObject,Mockito.times(1)).withExposedPorts(Mockito.any(ExposedPort.class));
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.createContainerCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.CREATE_CONTAINER;
    }

}
