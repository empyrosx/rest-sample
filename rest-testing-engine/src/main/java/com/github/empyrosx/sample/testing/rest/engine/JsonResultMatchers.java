package com.github.empyrosx.sample.testing.rest.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;

import javax.ws.rs.core.Response;
import java.util.List;

public class JsonResultMatchers {

    public <T> ResultMatcher equalTo(T expected) {
        return new ResultMatcher() {
            @Override
            public void match(Response result) throws Exception {
                Object actualAsString = result.readEntity(expected.getClass());
//                ObjectReader reader = new ObjectMapper().reader(expected.getClass());
//                List<T> actual = reader.<T>readValues(actualAsString).readAll();
                Assert.assertThat(actualAsString, CoreMatchers.equalTo(expected));
            }
        };
    }

    public <T> ResultMatcher listEqualTo(List<T> expected, Class clazz) {
        return new ResultMatcher() {
            @Override
            public void match(Response result) throws Exception {
                String actualAsString = result.readEntity(String.class);
                ObjectReader reader = new ObjectMapper().reader(clazz);
                List<T> actual = reader.<T>readValues(actualAsString).readAll();
                Assert.assertThat(actual, CoreMatchers.equalTo(expected));
            }
        };
    }
}
