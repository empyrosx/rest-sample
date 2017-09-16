package com.github.empyrosx.sample.testing.rest.engine;

import javax.inject.Inject;
import java.lang.reflect.Field;

public class MockInjector implements Injector {

    private MockStore mockStore;

    public MockInjector(MockStore mockStore) {
        this.mockStore = mockStore;
    }

    public final <T> void inject(T target, Field field) {
        if (field.isAnnotationPresent(Inject.class)) {
            Class<?> fieldClass = field.getType();
            Object ejb = mockStore.findInstanceByClass(fieldClass);
            InjectionUtils.setFieldValue(target, field, ejb);
        }
    }


}
