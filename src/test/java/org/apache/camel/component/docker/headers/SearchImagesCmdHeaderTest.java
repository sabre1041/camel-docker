package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.SearchImagesCmd;

import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Search Image Request headers are applied properly
 */
public class SearchImagesCmdHeaderTest extends BaseDockerHeaderTest<SearchImagesCmd> {

    @Mock
    private SearchImagesCmd mockObject;
    
    @Test
    public void searchImagesHeaderTest() {
        
        String term = "dockerTerm";
        
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_TERM, term);

        template.sendBodyAndHeaders("direct:in", "",headers);

        Mockito.verify(dockerClient,Mockito.times(1)).searchImagesCmd(Mockito.eq(term));
        
        
    }

    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.searchImagesCmd(Mockito.anyString())).thenReturn(mockObject);
    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.SEARCH_IMAGES;
    }

}
