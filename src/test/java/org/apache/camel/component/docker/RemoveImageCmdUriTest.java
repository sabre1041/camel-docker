package org.apache.camel.component.docker;

import com.github.dockerjava.api.command.RemoveImageCmd;

import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.docker.headers.BaseDockerHeaderTest;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Remove Image Request URI parameters are applied properly
 */
public class RemoveImageCmdUriTest extends BaseDockerHeaderTest<RemoveImageCmd> {

    private String imageId = "be29975e0098";
    private Boolean noPrune = false;
    private Boolean force = true;
    
    @Mock
    private RemoveImageCmd mockObject;
    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            
            @Override
            public void configure() throws Exception {
                from("direct:in").to("docker://"+getOperation().toString()+"?imageId="+imageId+"&noPrune="+noPrune+"&force="+force);
                
            }
        };
        
    } 
    
    @Test
    public void removeImageHeaderTest() {
        

        
        Map<String,Object> headers = getDefaultParameters();
        
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
