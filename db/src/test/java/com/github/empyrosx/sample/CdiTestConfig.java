package com.github.empyrosx.sample;

import com.github.database.rider.core.util.EntityManagerProvider;
import com.github.empyrosx.sample.beans.CdiConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.persistence.EntityManager;

/**
 * Created by empyros on 28.08.17.
 */
@Specializes
@ApplicationScoped
public class CdiTestConfig extends CdiConfig {


    @Produces
    public EntityManager produce() {
        synchronized (this) {
            return EntityManagerProvider.instance("sample").em();
        }
    }

}
