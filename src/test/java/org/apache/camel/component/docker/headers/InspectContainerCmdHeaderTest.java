package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.InspectContainerCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Inspect Container Request headers are applied properly
 */
public class InspectContainerCmdHeaderTest extends BaseDockerHeaderTest<InspectContainerCmd> {
    
    @Mock
    private InspectContainerCmd mockObject;
    
    @Test
    public void inspectContainerHeaderTest() {
        
        String containerId = "9c09acd48a25";
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_CONTAINER_ID, containerId);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
                
        Mockito.verify(dockerClient,Mockito.times(1)).inspectContainerCmd(containerId);

    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.inspectContainerCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.INSPECT_CONTAINER;
    }

}
