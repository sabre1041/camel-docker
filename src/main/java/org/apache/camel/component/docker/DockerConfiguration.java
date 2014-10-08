package org.apache.camel.component.docker;

import com.github.dockerjava.api.DockerClient;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.component.docker.exception.DockerException;

public class DockerConfiguration {


    private Map<String, Object> parameters = new HashMap<String, Object>();
    private Map<DockerClientProfile, DockerClient> clients = new HashMap<DockerClientProfile, DockerClient>();

    private static final String DEFAULT_DOCKER_HOST = "localhost";
    private static final int DEFAULT_DOCKER_PORT = 5000;
    
    private DockerOperation operation;

    public void setClient(DockerClientProfile clientProfile, DockerClient client) {
        clients.put(clientProfile, client);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public DockerOperation getOperation() {
        return operation;
    }

    public void setOperation(DockerOperation operation) {
        this.operation = operation;
    }

    public String getDefaultHost() {
        return DEFAULT_DOCKER_HOST;
    }

    public Integer getDefaultPort() {
        return DEFAULT_DOCKER_PORT;
    }

    public DockerClient getClient(DockerClientProfile clientProfile) throws DockerException {
        return clients.get(clientProfile);

    }

}
