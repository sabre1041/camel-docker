package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.ListContainersCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates List Containers Request headers are applied properly
 */
public class ListContainersCmdHeaderTest extends BaseDockerHeaderTest<ListContainersCmd> {
    
    @Mock
    private ListContainersCmd mockObject;
    
    @Test
    public void listContainerHeaderTest() {
        
        boolean showSize = true;
        boolean showAll = true;
        int limit = 2;
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_LIMIT, limit);
        headers.put(DockerConstants.DOCKER_SHOW_ALL, showAll);
        headers.put(DockerConstants.DOCKER_SHOW_SIZE, showSize);


        
        template.sendBodyAndHeaders("direct:in", "",headers);
                
        Mockito.verify(dockerClient,Mockito.times(1)).listContainersCmd();
        Mockito.verify(mockObject,Mockito.times(1)).withShowAll(Mockito.eq(showAll));
        Mockito.verify(mockObject,Mockito.times(1)).withShowSize(Mockito.eq(showSize));
        Mockito.verify(mockObject,Mockito.times(1)).withLimit(Mockito.eq(limit));

        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.listContainersCmd()).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.LIST_CONTAINERS;
    }

}
