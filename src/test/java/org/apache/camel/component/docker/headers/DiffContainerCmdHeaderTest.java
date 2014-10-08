package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.ContainerDiffCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Container Diff Request headers are parsed properly
 */
public class DiffContainerCmdHeaderTest extends BaseDockerHeaderTest<ContainerDiffCmd> {

    @Mock
    private ContainerDiffCmd mockObject;
    
    @Test
    public void containerDiffHeaderTest() {
                
        String containerId = "9c09acd48a25";
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_CONTAINER_ID, containerId);
        
        template.sendBodyAndHeaders("direct:in", "",headers);
        
        Mockito.verify(dockerClient,Mockito.times(1)).containerDiffCmd(containerId);
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.containerDiffCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.DIFF_CONTAINER;
    }

}