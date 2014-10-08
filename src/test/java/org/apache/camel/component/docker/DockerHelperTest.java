package org.apache.camel.component.docker;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DockerHelperTest {
    
    @Test
    public void transformHeaderTestFromHeader() {
        
        String headerField = DockerHelper.transformFromHeaderName(DockerConstants.DOCKER_REGISTRY);
        
        assertEquals("registry", headerField);
    }
    
    

}
