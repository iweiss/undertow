package io.undertow.websockets.jsr;

import io.undertow.servlet.api.InstanceFactory;
import io.undertow.util.PathTemplate;

import javax.websocket.Endpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Stuart Douglas
 */
public class ConfiguredServerEndpoint {

    private final ServerEndpointConfig endpointConfiguration;
    private final InstanceFactory<Endpoint> endpointFactory;
    private final PathTemplate pathTemplate;
    private final EncodingFactory encodingFactory;
    private final Set<Session> openSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());

    public ConfiguredServerEndpoint(final ServerEndpointConfig endpointConfiguration, final InstanceFactory<Endpoint> endpointFactory, final PathTemplate pathTemplate, final EncodingFactory encodingFactory) {
        this.endpointConfiguration = endpointConfiguration;
        this.endpointFactory = endpointFactory;
        this.pathTemplate = pathTemplate;
        this.encodingFactory = encodingFactory;
    }

    public ServerEndpointConfig getEndpointConfiguration() {
        return endpointConfiguration;
    }

    public InstanceFactory<Endpoint> getEndpointFactory() {
        return endpointFactory;
    }

    public PathTemplate getPathTemplate() {
        return pathTemplate;
    }

    public EncodingFactory getEncodingFactory() {
        return encodingFactory;
    }

    public Set<Session> getOpenSessions() {
        return openSessions;
    }
}
