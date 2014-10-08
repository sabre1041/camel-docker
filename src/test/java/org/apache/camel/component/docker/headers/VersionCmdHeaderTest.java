package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.VersionCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Version Request headers are parsed properly
 */
public class VersionCmdHeaderTest extends BaseDockerHeaderTest<VersionCmd> {

    @Mock
    private VersionCmd mockObject;
    
    @Test
    public void pingHeaderTest() {
                
        Map<String,Object> headers = getDefaultParameters();
        
        template.sendBodyAndHeaders("direct:in", "",headers);
        
        Mockito.verify(dockerClient,Mockito.times(1)).versionCmd();
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.versionCmd()).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.VERSION;
    }

}