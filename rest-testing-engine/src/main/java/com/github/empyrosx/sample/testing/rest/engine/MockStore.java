package com.github.empyrosx.sample.testing.rest.engine;

import org.mockito.Mock;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MockStore {

    private Map<Class, Object> instanceByClass = new HashMap<>();

    public final <T> void add(T instance, Field field) {
        if (field.isAnnotationPresent(Mock.class)) {
            Object mock = InjectionUtils.readFieldValue(instance, field);
            if (mock != null) {
                registerByImplementedInterfaces(mock);
                registerByClass(field.getType(), mock);
            }
        }
    }

    private void registerByImplementedInterfaces(Object instance) {
        Class<?>[] interfaces = instance.getClass().getInterfaces();
        for (Class<?> implementedInterface : interfaces) {
            instanceByClass.put(implementedInterface, instance);
        }
    }

    private void registerByClass(Class clazz, Object instance) {
        instanceByClass.put(clazz, instance);
    }

    public Object findInstanceByClass(Class<?> clazz) {
        return instanceByClass.get(clazz);
    }

}
