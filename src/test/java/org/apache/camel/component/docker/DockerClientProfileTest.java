package org.apache.camel.component.docker;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Validates the {@link DockerClientProfile}
 */
public class DockerClientProfileTest {
    
    @Test
    public void clientProfileTest() {
        
        String host = "host";
        String email = "docker@camel.apache.org";
        String username = "user";
        String password = "password";
        Integer port = 2241;
        Integer requestTimeout = 40;
        boolean secure = true;
        
        
        DockerClientProfile clientProfile1 = new DockerClientProfile();
        clientProfile1.setHost(host);
        clientProfile1.setEmail(email);
        clientProfile1.setUsername(username);
        clientProfile1.setPassword(password);
        clientProfile1.setPort(port);
        clientProfile1.setRequestTimeout(requestTimeout);
        clientProfile1.setSecure(secure);
        
        DockerClientProfile clientProfile2 = new DockerClientProfile();
        clientProfile2.setHost(host);
        clientProfile2.setEmail(email);
        clientProfile2.setUsername(username);
        clientProfile2.setPassword(password);
        clientProfile2.setPort(port);
        clientProfile2.setRequestTimeout(requestTimeout);
        clientProfile2.setSecure(secure);
        
        assertEquals(clientProfile1, clientProfile2);
    }

}
