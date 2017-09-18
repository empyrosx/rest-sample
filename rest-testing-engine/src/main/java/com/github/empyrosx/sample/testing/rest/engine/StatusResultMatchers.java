package com.github.empyrosx.sample.testing.rest.engine;

import org.apache.http.HttpStatus;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class StatusResultMatchers  {

    public ResultMatcher isOk() {
        return matcher(HttpStatus.SC_OK);
    }

    private ResultMatcher matcher(int status) {
        return new ResultMatcher() {
            @Override
            public void match(Response result) {
                assertEquals("Status", status, result.getStatus());
            }
        };
    }

}
