package com.github.empyrosx.sample.testing.rest.engine;

import javax.ws.rs.core.Response;

public interface ResultMatcher {

    void match(Response result) throws Exception;
}
