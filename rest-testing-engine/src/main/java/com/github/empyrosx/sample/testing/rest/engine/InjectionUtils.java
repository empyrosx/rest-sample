package com.github.empyrosx.sample.testing.rest.engine;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

public class InjectionUtils {

    public static <T> void setFieldValue(T target, Field field, Object value) {
        try {
            FieldUtils.writeField(field, target, value, true);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed injecting to " + getFieldDescription(field.getName(), target), e);
        }
    }

    public static <T, O> O readFieldValue(T target, Field field) {
        try {
            return (O) FieldUtils.readField(field, target, true);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to read from " + getFieldDescription(field.getName(), target), e);
        }
    }

    public static <T, O> O readFieldValue(T target, String fieldName) {
        try {
            return (O) FieldUtils.readField(target, fieldName, true);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to read from " + getFieldDescription(fieldName, target), e);
        }
    }

    public static <T> String getFieldDescription(String fieldName, T target) {
        Class<?> targetClass = target.getClass();
        return "field '" + fieldName + "' declared in " + targetClass;
    }

}
