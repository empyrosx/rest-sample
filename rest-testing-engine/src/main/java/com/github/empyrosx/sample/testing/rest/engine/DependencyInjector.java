package com.github.empyrosx.sample.testing.rest.engine;

import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public class DependencyInjector {

    private MockStore mockStore;
    private List<Injector> injectors;

    public DependencyInjector() {
        mockStore = new MockStore();
        injectors = Collections.singletonList(new MockInjector(mockStore));
    }

    /**
     * Injects all possible fields.
     *
     * @param pojo any pojo
     */
    public void injectDependencies(Object pojo) {
        if (pojo == null) {
            return;
        }

        for (Class clazz = pojo.getClass(); !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Injector injector : injectors) {
                for (Field field : fields) {
                    injector.inject(pojo, field);
                }
            }
        }
    }

    /**
     * Initializes all mocks in test class and registers them in store.
     *
     * @param testClass test class instance
     */
    public void createMocks(Object testClass) {
        if (testClass == null) {
            return;
        }

        // fills mock fields
        MockitoAnnotations.initMocks(this);

        // Loop over the class hierarchy of the target pojo
        for (Class clazz = testClass.getClass(); !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                mockStore.add(testClass, field);
            }
        }
    }

}
