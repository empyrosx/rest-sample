package com.github.empyrosx.sample.testing.rest.engine;

public class ResteasyResponceMatchers {

    public static StatusResultMatchers status() {
        return new StatusResultMatchers();
    }

    public static ContentResultMatchers content() {
        return new ContentResultMatchers();
    }

    public static JsonResultMatchers json() {
        return new JsonResultMatchers();
    }


}
