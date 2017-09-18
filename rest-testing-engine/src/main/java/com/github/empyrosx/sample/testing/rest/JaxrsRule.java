package com.github.empyrosx.sample.testing.rest;

import com.github.empyrosx.sample.testing.rest.engine.DependencyInjector;
import com.github.empyrosx.sample.testing.rest.engine.JaxrsServer;
import com.github.empyrosx.sample.testing.rest.engine.RestRequest;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.MediaType;

public class JaxrsRule implements MethodRule {

    private Class[] controllerClasses;
    private JaxrsServer jaxrsServer;
    private DependencyInjector dependencyInjector;

    public JaxrsRule(Class... controllerClasses) {
        this.controllerClasses = controllerClasses;

        dependencyInjector = new DependencyInjector();
//        dependencyInjector.createMocks(this);
    }

    private void startJaxRsServer(Object target) {
        jaxrsServer = new JaxrsServer();
        jaxrsServer.setPort(0);
        jaxrsServer.start();

        MockitoAnnotations.initMocks(target);
        dependencyInjector.createMocks(target);
        initControllers(controllerClasses);
    }

    private void initControllers(Class[] resourceClasses) {
        for (Class aClass : resourceClasses) {
            Object resourceInstance;
            try {
                resourceInstance = aClass.newInstance();
            } catch (Exception e1) {
                throw new IllegalArgumentException(e1);
            }
            dependencyInjector.injectDependencies(resourceInstance);
            getDeployment().getRegistry().addSingletonResource(resourceInstance);
        }
    }

    private void shutdownJaxRsServer() {
        jaxrsServer.stop();
        getDeployment().stop();
    }

    protected ResteasyDeployment getDeployment() {
        return jaxrsServer.getDeployment();
    }

    public RestRequest jsonRequest(String uri) {
        return new RestRequest(uri, jaxrsServer.getLocalPort()).accept(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                startJaxRsServer(target);

                try {
                    base.evaluate();

                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                } finally {
                    shutdownJaxRsServer();
                }
            }
        };
    }
}
