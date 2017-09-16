package com.github.empyrosx.sample.rest;

import org.jboss.resteasy.spi.ResteasyDeployment;
import org.junit.After;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.MediaType;

public abstract class BaseRestTest {

    private Class[] controllerClasses;
    private JaxrsServer jaxrsServer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        DependencyInjector.getInstance().reset();
        DependencyInjector.getInstance().injectDependencies(this);

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
            DependencyInjector.getInstance().injectDependencies(resourceInstance);
            getDeployment().getRegistry().addSingletonResource(resourceInstance);
        }
    }

    protected abstract Class[] getControllers();

    @After
    public void tearDown() throws Exception {
        shutdownJaxRsServer();
    }

    public RestRequest jsonRequest(String uri) {
        return new RestRequest(uri, jaxrsServer.getLocalPort()).accept(MediaType.APPLICATION_JSON_TYPE);
    }

    public void startJaxRsServer() {
        jaxrsServer = new JaxrsServer();
        jaxrsServer.setPort(0);
        jaxrsServer.start();

        initControllers(controllerClasses);
    }

    public void shutdownJaxRsServer() {
        jaxrsServer.stop();
        getDeployment().stop();
    }

    public ResteasyDeployment getDeployment() {
        return jaxrsServer.getDeployment();
    }
}
