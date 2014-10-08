package org.apache.camel.component.docker;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.component.docker.exception.DockerException;
import org.apache.camel.impl.DefaultComponent;

/**
 * Represents the component that manages {@link DockerEndpoint}.
 */
public class DockerComponent extends DefaultComponent {
    
    private DockerConfiguration configuration;
        
    public DockerComponent() {
        
    }
    
    public DockerComponent(DockerConfiguration configuration) {
        this.configuration = configuration;
    }
    
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        
        DockerConfiguration configuration = getConfiguration();
        
        String normalizedRemaining = remaining.replaceAll("/", "");
        
        DockerOperation operation = DockerOperation.getDockerOperation(normalizedRemaining);
        
        if(operation == null) {
            throw new DockerException(remaining + " is not a valid operation");
        }
        
        configuration.setOperation(operation);
        
        // Validate URI Parameters
        DockerHelper.validateParameters(operation, parameters);
        
        Endpoint endpoint = new DockerEndpoint(uri, this, configuration);
        setProperties(configuration, parameters);
        configuration.setParameters(parameters);
                
        return endpoint;
    }
    
    protected DockerConfiguration getConfiguration() {
        if(configuration == null) {
            configuration = new DockerConfiguration();
        }
        
        return configuration;
    }
        
}
