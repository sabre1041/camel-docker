package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.PushImageCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerClientProfile;
import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Push Image Request headers are applied properly
 */
public class PushImageCmdHeaderTest extends BaseDockerHeaderTest<PushImageCmd> {
    
    @Mock
    private PushImageCmd mockObject;
    
    private String userName = "jdoe";
    private String password = "password";
    private String email = "jdoe@example.com";
    private String serverAddress = "http://docker.io/v1";
    private String name = "imagename";

    
    @Test
    public void pushImageHeaderTest() {
        
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_USERNAME, userName);
        headers.put(DockerConstants.DOCKER_PASSWORD, password);
        headers.put(DockerConstants.DOCKER_EMAIL, email);
        headers.put(DockerConstants.DOCKER_SERVER_ADDRESS, serverAddress);
        headers.put(DockerConstants.DOCKER_NAME, name);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
        
        Mockito.verify(dockerClient,Mockito.times(1)).pushImageCmd(name);
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.pushImageCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.PUSH_IMAGE;
    }
    
    @Override
    public DockerClientProfile getClientProfile() {
       DockerClientProfile clientProfile = super.getClientProfile();
       clientProfile.setEmail(email);
       clientProfile.setPassword(password);
       clientProfile.setUsername(userName);
       clientProfile.setServerAddress(serverAddress);
       
       return clientProfile;
       
    }


}
