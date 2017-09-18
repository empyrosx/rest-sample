package com.github.empyrosx.sample.testing.rest.engine;

import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matcher;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class JsonPathExpectationsHelper {

    private final String expression;

    private final JsonPath jsonPath;


    public JsonPathExpectationsHelper(String expression, Object... args) {
        this.expression = String.format(expression, args);
        this.jsonPath = JsonPath.compile(this.expression);
    }

    public void assertValue(String content, Object expectedValue) {
        Object actualValue = evaluateJsonPath(content);
        if ((actualValue instanceof List) && !(expectedValue instanceof List)) {
            @SuppressWarnings("rawtypes")
            List actualValueList = (List) actualValue;
            if (actualValueList.isEmpty()) {
                fail("No matching value at JSON path \"" + this.expression + "\"");
            }
            if (actualValueList.size() != 1) {
                fail("Got a list of values " + actualValue + " instead of the expected single value " + expectedValue);
            }
            actualValue = actualValueList.get(0);
        }
        else if (actualValue != null && expectedValue != null) {
            if (!actualValue.getClass().equals(expectedValue.getClass())) {
                actualValue = evaluateJsonPath(content, expectedValue.getClass());
            }
        }
        assertEquals("JSON path \"" + this.expression + "\"", expectedValue, actualValue);
    }

    public static void fail(String message) {
        throw new AssertionError(message);
    }

    private Object evaluateJsonPath(String content) {
        String message = "No value at JSON path \"" + this.expression + "\", exception: ";
        try {
            return this.jsonPath.read(content);
        }
        catch (Throwable ex) {
            throw new AssertionError(message + ex.getMessage());
        }
    }

    private Object evaluateJsonPath(String content, Class<?> targetType) {
        String message = "No value at JSON path \"" + this.expression + "\", exception: ";
        try {
            return JsonPath.parse(content).read(this.expression, targetType);
        }
        catch (Throwable ex) {
            throw new AssertionError(message + ex.getMessage());
        }
    }


}
