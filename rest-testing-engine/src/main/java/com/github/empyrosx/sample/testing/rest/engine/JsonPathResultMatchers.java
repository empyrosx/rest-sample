package com.github.empyrosx.sample.testing.rest.engine;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.StringStartsWith;

import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;

public class JsonPathResultMatchers {

    private final JsonPathExpectationsHelper jsonPathHelper;

    private String prefix;

    public static JsonPathResultMatchers jsonPath(String expression, Object... args) {
        return new JsonPathResultMatchers(expression, args);
    }

    protected JsonPathResultMatchers(String expression, Object... args) {
        this.jsonPathHelper = new JsonPathExpectationsHelper(expression, args);
    }

    /**
     * Evaluate the JSON path expression against the response content and
     * assert that the result is equal to the supplied value.
     */
    public ResultMatcher value(final Object expectedValue) {
        return new ResultMatcher() {
            @Override
            public void match(Response result) throws Exception {
                jsonPathHelper.assertValue(getContent(result), expectedValue);
            }
        };
    }

    private String getContent(Response result) throws UnsupportedEncodingException {
        String content = result.readEntity(String.class);
        if (StringUtils.isNotEmpty(this.prefix)) {
            try {
                String reason = String.format("Expected a JSON payload prefixed with \"%s\" but found: %s",
                        this.prefix, StringUtils.wrap(content.substring(0, this.prefix.length()), "'"));
                MatcherAssert.assertThat(reason, content, StringStartsWith.startsWith(this.prefix));
                return content.substring(this.prefix.length());
            } catch (StringIndexOutOfBoundsException oobe) {
                throw new AssertionError(
                        "JSON prefix \"" + this.prefix + "\" not found, exception: " + oobe.getMessage());
            }
        } else {
            return content;
        }
    }


}
