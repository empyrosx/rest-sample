package com.github.empyrosx.sample.testing.rest.engine;

import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;

import java.net.ServerSocket;

public class JaxrsServer extends TJWSEmbeddedJaxrsServer {

    private int port;

    @Override
    public void start() {
        super.start();
        port = gerPort();
    }

    private int gerPort() {
        Object acceptor = getFromPrivateField(server, "acceptor");
        Object socket = getFromPrivateField(acceptor, "socket");
        return ((ServerSocket) socket).getLocalPort();
    }

    private Object getFromPrivateField(Object obj, String fieldName) {
        return InjectionUtils.readFieldValue(obj, fieldName);
    }

    public int getLocalPort() {
        return port;
    }
}
