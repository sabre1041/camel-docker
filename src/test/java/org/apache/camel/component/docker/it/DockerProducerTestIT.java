package org.apache.camel.component.docker.it;

import java.util.concurrent.TimeUnit;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Integration test listing images on Docker Platform
 */
public class DockerProducerTestIT extends CamelTestSupport {

    private String host = "192.168.59.103";
    private String port = "2375";
    
    @Test
    public void testDocker() throws Exception {
        template.sendBody("direct:in", "");
        
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);       
        
        assertMockEndpointsSatisfied(60, TimeUnit.SECONDS);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:in")
                .to("docker://listimages?host="+host+"&port="+port)
                .log("${body}")
                  .to("mock:result");
            }
        };
    }
}
