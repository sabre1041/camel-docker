package org.apache.camel.component.docker;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.camel.component.docker.exception.DockerException;
import org.apache.camel.util.ObjectHelper;

/**
 * The elements representing a client initiating a connection to Docker
 */
public class DockerClientProfile {
    
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String email;
    private String serverAddress;
    private Integer requestTimeout;
    private boolean secure;
    
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getServerAddress() {
        return serverAddress;
    }
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }
    
    public Integer getRequestTimeout() {
        return requestTimeout;
    }
    public void setRequestTimeout(Integer requestTimeout) {
        this.requestTimeout = requestTimeout;
    }
    public boolean isSecure() {
        return secure;
    }
    public void setSecure(boolean secure) {
        this.secure = secure;
    }
    public String toUrl() throws DockerException {
        ObjectHelper.notNull(this.host, "host");
        ObjectHelper.notNull(this.port, "port");
        
        URL uri;
        String secure = this.secure ? "https" : "http";
        try {
            uri = new URL(secure, this.host, this.port, "");
        } catch (MalformedURLException e) {
           throw new DockerException(e);
        }

        return uri.toString();
        
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((port == null) ? 0 : port.hashCode());
        result = prime * result + ((requestTimeout == null) ? 0 : requestTimeout.hashCode());
        result = prime * result + (secure ? 1231 : 1237);
        result = prime * result + ((serverAddress == null) ? 0 : serverAddress.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DockerClientProfile other = (DockerClientProfile)obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (host == null) {
            if (other.host != null)
                return false;
        } else if (!host.equals(other.host))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (port == null) {
            if (other.port != null)
                return false;
        } else if (!port.equals(other.port))
            return false;
        if (requestTimeout == null) {
            if (other.requestTimeout != null)
                return false;
        } else if (!requestTimeout.equals(other.requestTimeout))
            return false;
        if (secure != other.secure)
            return false;
        if (serverAddress == null) {
            if (other.serverAddress != null)
                return false;
        } else if (!serverAddress.equals(other.serverAddress))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
  
}
