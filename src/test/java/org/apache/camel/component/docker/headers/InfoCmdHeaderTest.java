package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.InfoCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Info Request headers are parsed properly
 */
public class InfoCmdHeaderTest extends BaseDockerHeaderTest<InfoCmd> {

    @Mock
    private InfoCmd mockObject;
    
    @Test
    public void listImageHeaderTest() {
                
        Map<String,Object> headers = getDefaultParameters();
        
        template.sendBodyAndHeaders("direct:in", "",headers);
        
        Mockito.verify(dockerClient,Mockito.times(1)).infoCmd();
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.infoCmd()).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.INFO;
    }

}