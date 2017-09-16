package com.github.empyrosx.sample.rest;

import java.lang.reflect.Field;

public interface Injector {
    <T> void inject(T target, Field field);
    void reset();
}
