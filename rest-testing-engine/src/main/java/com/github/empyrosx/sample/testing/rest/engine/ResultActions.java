package com.github.empyrosx.sample.testing.rest.engine;

public interface ResultActions {

    ResultActions andExpect(ResultMatcher matcher) throws Exception;
}
