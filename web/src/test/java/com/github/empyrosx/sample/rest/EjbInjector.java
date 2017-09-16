package com.github.empyrosx.sample.rest;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

// Lookup and inject to @EJB annotated member variables
public class EjbInjector extends BaseInjector {

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return Inject.class;
    }

    @Override
    public <T> void doInject(T target, Field field) {
        Class<?> fieldClass = field.getType();
        Object ejb = findInstanceByClass(fieldClass);
        InjectionUtils.assignObjectToField(target, field, ejb);
    }
}
