package com.github.empyrosx.sample.testing.rest.engine;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RestRequest {

    private String uri;
    private int port;
    private String basicCredentials;

    private MediaType contentType = MediaType.APPLICATION_XML_TYPE;
    private MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
    private Object body;
    private Map<String, Object> queryParams = new HashMap<>();

    public RestRequest(String uri, int port) {
        this.uri = uri;
        this.port = port;
    }

    public RestRequest accept(MediaType acceptMediaType) {
        contentType = acceptMediaType;
        return this;
    }

    public RestRequest header(String key, Object value) {
        headers.add(key, value);
        return this;
    }

    public RestRequest body(Object body) {
        this.body = body;
        return this;
    }

    public RestRequest withFormParam(String name, String value) {
        String entry = name + "=" + value;
        this.body = body == null ? entry : body.toString() + "&" + entry;
        return this;
    }

    public RestRequest basicAuth(String userName, String password) {
        try {
            basicCredentials = URLEncoder.encode(userName, "UTF-8") + ":" + URLEncoder.encode(password, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            basicCredentials = userName + ":" + password;
        }
        return this;
    }

    public RestRequest queryParam(String param, Object value) {
        queryParams.put(param, value);
        return this;
    }

    public String get() {
        return doHttpMethod("GET");
    }

    public String put() {
        return doHttpMethod("PUT");
    }

    public String post() {
        return doHttpMethod("POST");
    }

    public String delete() {
        return doHttpMethod("DELETE");
    }

    private String doHttpMethod(String method) {
        String baseUri = basicCredentials != null ? "http://" + basicCredentials + "@localhost" : "http://localhost";
        UriBuilder path = UriBuilder.fromUri(baseUri).port(port).path(uri);
        WebTarget webTarget = ClientBuilder.newBuilder().build().target(path.build());

        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
        }

        Response response;
        try {
            if (body != null) {
                response = webTarget.request().headers(headers).build(method, Entity.entity(body, contentType)).invoke();
            } else {
                response = webTarget.request().headers(headers).build(method).invoke();
            }
        } catch (Exception e) {
            throw new RuntimeException(method + " failed", e);
        }

        return toString(response);
    }

    private String toString(Response response) {
        Response.Status responseStatus = Response.Status.fromStatusCode(response.getStatus());

        if (responseStatus == Response.Status.NO_CONTENT) {
            return null;
        }
        return response.readEntity(String.class);
    }
}
