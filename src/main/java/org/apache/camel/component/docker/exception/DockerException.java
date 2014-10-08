package org.apache.camel.component.docker.exception;

/**
 * Errors occurring during the processing of the Docker component
 */
public class DockerException extends Exception {

    private static final long serialVersionUID = 1L;

    public DockerException(String message) {
        super(message);
    }
    
    public DockerException(Throwable throwable) {
        super(throwable);
    }

}
