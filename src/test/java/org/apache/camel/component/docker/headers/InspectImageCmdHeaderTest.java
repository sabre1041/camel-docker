package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.InspectImageCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Inspect Image Request headers are parsed properly
 */
public class InspectImageCmdHeaderTest extends BaseDockerHeaderTest<InspectImageCmd> {

    @Mock
    private InspectImageCmd mockObject;
    
    @Test
    public void listImageHeaderTest() {
        
        String imageId = "be29975e0098";
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_IMAGE_ID, imageId);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
        
        Mockito.verify(dockerClient,Mockito.times(1)).inspectImageCmd(Mockito.eq(imageId));
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.inspectImageCmd(Mockito.anyString())).thenReturn(mockObject);

        
    }

    @Override
    protected DockerOperation getOperation() {
       return DockerOperation.INSPECT_IMAGE;
    }

}
