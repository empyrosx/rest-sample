package com.github.empyrosx.sample.testing.rest;

import com.github.empyrosx.sample.testing.rest.engine.DependencyInjector;
import com.github.empyrosx.sample.testing.rest.engine.JaxrsServer;
import com.github.empyrosx.sample.testing.rest.engine.RestRequest;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.junit.After;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.MediaType;

public abstract class BaseRestTest {

    private Class[] controllerClasses;
    private JaxrsServer jaxrsServer;
    private DependencyInjector dependencyInjector;

    @Before
    public void setUp() throws Exception {
        dependencyInjector = new DependencyInjector();
        dependencyInjector.createMocks(this);

        controllerClasses = getControllers();
        startJaxRsServer();
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

    protected abstract Class[] getControllers();

    @After
    public void tearDown() throws Exception {
        shutdownJaxRsServer();
    }

    protected RestRequest jsonRequest(String uri) {
        return new RestRequest(uri, jaxrsServer.getLocalPort()).accept(MediaType.APPLICATION_JSON_TYPE);
    }

    private void startJaxRsServer() {
        jaxrsServer = new JaxrsServer();
        jaxrsServer.setPort(0);
        jaxrsServer.start();

        initControllers(controllerClasses);
    }

    private void shutdownJaxRsServer() {
        jaxrsServer.stop();
        getDeployment().stop();
    }

    protected ResteasyDeployment getDeployment() {
        return jaxrsServer.getDeployment();
    }
}
