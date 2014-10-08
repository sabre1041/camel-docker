package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.LogContainerCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Log Container Request headers are applied properly
 */
public class LogContainerCmdHeaderTest extends BaseDockerHeaderTest<LogContainerCmd> {
    
    @Mock
    private LogContainerCmd mockObject;
    
    @Test
    public void logContainerHeaderTest() {
        
        String containerId = "9c09acd48a25";
        boolean stdOut = true;
        boolean stdErr = true;
        boolean followStream = true;
        boolean timestamps = true;
        boolean tailAll = true;
        int tail = 5;
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_CONTAINER_ID, containerId);
        headers.put(DockerConstants.DOCKER_FOLLOW_STREAM, followStream);
        headers.put(DockerConstants.DOCKER_STD_OUT, stdOut);
        headers.put(DockerConstants.DOCKER_STD_ERR, stdErr);
        headers.put(DockerConstants.DOCKER_TIMESTAMPS, timestamps);
        headers.put(DockerConstants.DOCKER_TAIL, tail);
        headers.put(DockerConstants.DOCKER_TAIL_ALL, tailAll);

        template.sendBodyAndHeaders("direct:in", "",headers);
                
        Mockito.verify(dockerClient,Mockito.times(1)).logContainerCmd(containerId);
        Mockito.verify(mockObject,Mockito.times(1)).withFollowStream(Mockito.eq(followStream));
        Mockito.verify(mockObject,Mockito.times(1)).withTail(Mockito.eq(tail));
        Mockito.verify(mockObject,Mockito.times(1)).withTailAll();
        Mockito.verify(mockObject,Mockito.times(1)).withStdErr(Mockito.eq(stdErr));
        Mockito.verify(mockObject,Mockito.times(1)).withStdOut(Mockito.eq(stdOut));
        Mockito.verify(mockObject,Mockito.times(1)).withTimestamps(Mockito.eq(timestamps));
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.logContainerCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.LOG_CONTAINER;
    }

}
