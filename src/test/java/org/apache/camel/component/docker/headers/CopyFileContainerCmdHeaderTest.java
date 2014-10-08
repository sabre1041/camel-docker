package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.CopyFileFromContainerCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Copy File from Container Request headers are applied properly
 */
public class CopyFileContainerCmdHeaderTest extends BaseDockerHeaderTest<CopyFileFromContainerCmd> {
    
    @Mock
    private CopyFileFromContainerCmd mockObject;
    
    @Test
    public void copyFileFromContainerHeaderTest() {
        
        String containerId = "9c09acd48a25";
        String resource = "/test";
        String hostPath = "/test/test2";
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_CONTAINER_ID, containerId);
        headers.put(DockerConstants.DOCKER_RESOURCE, resource);
        headers.put(DockerConstants.DOCKER_HOST_PATH, hostPath);


        template.sendBodyAndHeaders("direct:in", "",headers);
                
        Mockito.verify(dockerClient,Mockito.times(1)).copyFileFromContainerCmd(containerId, resource);
        Mockito.verify(mockObject,Mockito.times(1)).withHostPath(Mockito.eq(hostPath));
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.copyFileFromContainerCmd(Mockito.anyString(), Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.CONTAINER_COPY_FILE;
    }

}
