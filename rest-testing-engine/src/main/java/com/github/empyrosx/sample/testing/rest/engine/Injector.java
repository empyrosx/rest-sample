package com.github.empyrosx.sample.testing.rest.engine;

import java.lang.reflect.Field;

public interface Injector {

    <T> void inject(T target, Field field);
}
