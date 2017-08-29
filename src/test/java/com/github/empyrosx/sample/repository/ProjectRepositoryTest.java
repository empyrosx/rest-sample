package com.github.empyrosx.sample.repository;

import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.empyrosx.sample.AssertSqlCount;
import com.github.empyrosx.sample.rest.model.Project;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.flywaydb.core.Flyway;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by empyros on 28.08.17.
 */
@RunWith(CdiTestRunner.class)
public class ProjectRepositoryTest {

    private static String DB_URL = "jdbc:hsqldb:mem:sample";

    private static Flyway flyway;

    @Inject
    EntityManager entityManager;

    @Inject
    ProjectRepository repository;

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.
            instance(() -> flyway.getDataSource().getConnection());

    @BeforeClass
    public static void initMigration() throws SQLException {
        System.out.println(DB_URL);
        flyway = new Flyway();
        flyway.setDataSource(DB_URL, "sa", "");
        flyway.setLocations("db/migration");
        flyway.setValidateOnMigrate(true);
        flyway.migrate();
    }

    @Test
    @DataSet(value = "all-projects.xml", cleanBefore = true)
    public void testGetAllProjects() throws Exception {
        List<Project> actual = repository.findAll();
        assertEquals(2, actual.size());

        assertThat(actual, contains(new Project(1, "Web-consolidation"),
                new Project(2, "Web-budget")));

        AssertSqlCount.assertSelectCount(1);
    }
}