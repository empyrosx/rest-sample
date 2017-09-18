package com.github.empyrosx.sample.testing.rest.engine;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContentResultMatchers {

    public ResultMatcher contentType(final MediaType contentType) {
        return new ResultMatcher() {

            @Override
            public void match(Response result) throws Exception {
                MediaType actual = result.getMediaType();
                assertTrue("Content type not set", actual != null);
                assertEquals("Content type", contentType, actual);
            }
        };
    }

}
