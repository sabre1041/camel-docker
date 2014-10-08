package org.apache.camel.component.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.apache.camel.Message;
import org.apache.camel.component.docker.exception.DockerException;
import org.apache.camel.util.ObjectHelper;

/**
 * Methods for communicating with Docker
 */
public class DockerClientFactory {
    
    
    /**
     * Produces a {@link DockerClient} to communicate with Docker
     * 
     * @param dockerConfiguration
     * @param message the Camel message
     * @return a DockerClient
     * @throws DockerException
     */
    public static DockerClient getDockerClient(DockerConfiguration dockerConfiguration, Message message) throws DockerException {
        
        ObjectHelper.notNull(dockerConfiguration, "dockerConfiguration");
        
        Integer port = null;
        String host = null;
        DockerClient client = null;
       
            
        port = DockerHelper.getProperty(DockerConstants.DOCKER_PORT, dockerConfiguration, message, Integer.class);
        host = DockerHelper.getProperty(DockerConstants.DOCKER_HOST, dockerConfiguration, message, String.class);
       
        int uriPort = port != null ? port : dockerConfiguration.getDefaultPort();
        String uriHost = host != null ? host : dockerConfiguration.getDefaultHost();
        
        String username = DockerHelper.getProperty(DockerConstants.DOCKER_USERNAME, dockerConfiguration, message, String.class);
        String password = DockerHelper.getProperty(DockerConstants.DOCKER_PASSWORD, dockerConfiguration, message, String.class);
        String email = DockerHelper.getProperty(DockerConstants.DOCKER_EMAIL, dockerConfiguration, message, String.class);
        Integer requestTimeout = DockerHelper.getProperty(DockerConstants.DOCKER_API_REQUEST_TIMEOUT, dockerConfiguration, message, Integer.class);
        String serverAddress = DockerHelper.getProperty(DockerConstants.DOCKER_SERVER_ADDRESS, dockerConfiguration, message, String.class);
        Boolean secure = DockerHelper.getProperty(DockerConstants.DOCKER_SECURE, dockerConfiguration, message, Boolean.class);
        
        
        DockerClientProfile clientProfile = new DockerClientProfile();
        clientProfile.setHost(uriHost);
        clientProfile.setPort(uriPort);
        clientProfile.setEmail(email);
        clientProfile.setUsername(username);
        clientProfile.setPassword(password);
        clientProfile.setRequestTimeout(requestTimeout);
        clientProfile.setServerAddress(serverAddress);
        
        if(secure != null && secure) {
            clientProfile.setSecure(secure);
        }
        
        client = dockerConfiguration.getClient(clientProfile);
        
        if(client != null) {
            return client;
        }
        
        DockerClientConfig.DockerClientConfigBuilder configBuilder = new DockerClientConfig.DockerClientConfigBuilder()
        .withUsername(username)
        .withPassword(password)
        .withEmail(email)
        .withReadTimeout(requestTimeout)
        .withUri(clientProfile.toUrl());
        
        DockerClientConfig config = configBuilder.build();
        
        return DockerClientBuilder.getInstance(config).build();
        
    }

}
