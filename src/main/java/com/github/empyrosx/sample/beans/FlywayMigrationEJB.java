package com.github.empyrosx.sample.beans;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;
import java.util.logging.Logger;

/**
 * Created by empyros on 28.08.17.
 */
@Singleton
@Startup
public class FlywayMigrationEJB {

    private final Logger log = Logger.getLogger(FlywayMigrationEJB.class.getName());

    @Resource(lookup = "jdbc/__default")
    private DataSource dataSource;

    @PostConstruct
    private void onStartup() {
        if (dataSource == null) {
            log.severe("no datasource found to execute the db migrations!");
            throw new EJBException(
                    "no datasource found to execute the db migrations!");
        }

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        for (MigrationInfo i : flyway.info().all()) {
            log.info("migrate task: " + i.getVersion() + " : "
                    + i.getDescription() + " from file: " + i.getScript());
        }
        flyway.migrate();
    }
}
