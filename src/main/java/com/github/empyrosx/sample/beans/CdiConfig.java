package com.github.empyrosx.sample.beans;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by empyros on 26.08.17.
 */
public class CdiConfig {

    @Produces
    @Dependent
    @PersistenceContext(unitName = "sample")
    private EntityManager entityManager;
}
