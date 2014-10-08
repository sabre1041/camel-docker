package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.PingCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Ping Request headers are parsed properly
 */
public class PingCmdHeaderTest extends BaseDockerHeaderTest<PingCmd> {

    @Mock
    private PingCmd mockObject;
    
    @Test
    public void pingHeaderTest() {
                
        Map<String,Object> headers = getDefaultParameters();
        
        template.sendBodyAndHeaders("direct:in", "",headers);
        
        Mockito.verify(dockerClient,Mockito.times(1)).pingCmd();
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.pingCmd()).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.PING;
    }

}