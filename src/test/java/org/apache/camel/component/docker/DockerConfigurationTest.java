package org.apache.camel.component.docker;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;
import org.junit.Before;
import org.junit.Test;

public class DockerConfigurationTest {
    
    private DockerConfiguration configuration;
    
    @Before
    public void setupTest() {
        configuration = new DockerConfiguration();
    }
    
    @Test
    public void testPropertyFromHeader() {
        
        String host = "camelhost";
        
        Message message = new DefaultMessage();
        message.setHeader(DockerConstants.DOCKER_HOST, host);
        
        String configurationProp = DockerHelper.getProperty(DockerConstants.DOCKER_HOST, configuration, message, String.class);
        assertEquals(host, configurationProp);
    }
    
    @Test
    public void testPropertyfromEndpointProperties() {
        String host = "camelhost";
        
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put(DockerHelper.transformFromHeaderName(DockerConstants.DOCKER_HOST), host);
        configuration.setParameters(parameters);
        
        Message message = new DefaultMessage();
        String configurationProp = DockerHelper.getProperty(DockerConstants.DOCKER_HOST, configuration, message, String.class);
        assertEquals(host, configurationProp);


        
    }

}
