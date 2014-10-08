package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.ListImagesCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates List Image Request headers are parsed properly
 */
public class ListImagesCmdHeaderTest extends BaseDockerHeaderTest<ListImagesCmd> {

    
    @Mock
    private ListImagesCmd mockObject;
    
    @Test
    public void listImageHeaderTest() {
        
        String filter = "filter";
        Boolean showAll = true;
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_FILTER, filter);
        headers.put(DockerConstants.DOCKER_SHOW_ALL, showAll);

        
        template.sendBodyAndHeaders("direct:in", "",headers);
        
        Mockito.verify(dockerClient,Mockito.times(1)).listImagesCmd();
        Mockito.verify(mockObject,Mockito.times(1)).withFilter(Mockito.eq(filter));
        Mockito.verify(mockObject,Mockito.times(1)).withShowAll(Mockito.eq(showAll));
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.listImagesCmd()).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.LIST_IMAGES;
    }

}
