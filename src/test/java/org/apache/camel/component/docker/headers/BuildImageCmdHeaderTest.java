package org.apache.camel.component.docker.headers;

import com.github.dockerjava.api.command.BuildImageCmd;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.apache.camel.component.docker.DockerConstants;
import org.apache.camel.component.docker.DockerOperation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Validates Build Image Request headers are parsed properly
 */
public class BuildImageCmdHeaderTest extends BaseDockerHeaderTest<BuildImageCmd> {
    
    @Mock
    private BuildImageCmd mockObject;
    
    @Mock
    private InputStream inputStream;
    
    @Mock
    private File file;
    
    private String repository = "docker/empty";
    private boolean quiet = true;
    private boolean noCache = true;
    private boolean remove = true;
    private String tag = "1.0";
    
    @Test
    public void buildImageFromInputStreamHeaderTest() {
        
        template.sendBodyAndHeaders("direct:in", inputStream,getHeaders());
        
        Mockito.verify(dockerClient,Mockito.times(1)).buildImageCmd(Mockito.any(InputStream.class));
        Mockito.verify(mockObject,Mockito.times(1)).withQuiet();
        Mockito.verify(mockObject,Mockito.times(1)).withNoCache();
        Mockito.verify(mockObject,Mockito.times(1)).withRemove();
        Mockito.verify(mockObject,Mockito.times(1)).withTag(tag);
        
    }
    
    @Test
    public void buildImageFromFileHeaderTest() {
        
        template.sendBodyAndHeaders("direct:in", file,getHeaders());
        
        Mockito.verify(dockerClient,Mockito.times(1)).buildImageCmd(Mockito.any(File.class));
        Mockito.verify(mockObject,Mockito.times(1)).withQuiet();
        Mockito.verify(mockObject,Mockito.times(1)).withNoCache();
        Mockito.verify(mockObject,Mockito.times(1)).withRemove();
        Mockito.verify(mockObject,Mockito.times(1)).withTag(tag);
        
    }
    
    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.buildImageCmd(Mockito.any(InputStream.class))).thenReturn(mockObject);
        Mockito.when(dockerClient.buildImageCmd(Mockito.any(File.class))).thenReturn(mockObject);

    }

    @Override
    protected DockerOperation getOperation() {
        return DockerOperation.BUILD_IMAGE;
    }
    
    private Map<String,Object> getHeaders() {
        Map<String,Object> headers = getDefaultParameters();
        headers.put(DockerConstants.DOCKER_REPOSITORY, repository);
        headers.put(DockerConstants.DOCKER_QUIET, quiet);
        headers.put(DockerConstants.DOCKER_NO_CACHE, noCache);
        headers.put(DockerConstants.DOCKER_TAG, tag);
        headers.put(DockerConstants.DOCKER_REMOVE, remove);
        
        return headers;
    }

}
