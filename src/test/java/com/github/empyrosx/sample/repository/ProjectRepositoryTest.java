package com.github.empyrosx.sample.repository;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
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

    //    private static String DB_URL = "jdbc:hsqldb:file:test";
    private static String DB_URL = "jdbc:hsqldb:" + Paths.get("target").toAbsolutePath().toString() +
            "/sample";

    private static Flyway flyway;

    private static Connection connection;

    @Inject
    EntityManager entityManager;

    @Inject
    ProjectRepository repository;

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.
            instance(() -> flyway.getDataSource().getConnection());

    @BeforeClass
    public static void initMigration() throws SQLException {

//        try {
//            LogManager.getLogManager().readConfiguration(ProjectRepositoryTest.class.getResourceAsStream("logging.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println(DB_URL);
        flyway = new Flyway();
        flyway.setDataSource(DB_URL, "sa", "");
        flyway.setLocations("db/migration");
        flyway.setValidateOnMigrate(true);
        flyway.migrate();

//        connection = flyway.getDataSource().getConnection();
//        try (Statement stmt = connection.createStatement()) {
//            stmt.addBatch("INSERT INTO PUBLIC.project(id, name) VALUES (1, 'Erich')");
//            stmt.addBatch("INSERT INTO PUBLIC.project(id, name) VALUES (2, 'Richard')");
//            int[] result = stmt.executeBatch();
//            assertEquals(result.length, 2);
//        }
    }

    @Test
    @DataSet(value = "all-projects.xml", cleanBefore = true)
    public void testGetAllProjects() throws Exception {
        List<Project> actual = repository.findAll();
        assertEquals(2, actual.size());

        List<Project> expected = new ArrayList<>();
        expected.add(new Project(1, "Web-consolidation"));
        expected.add(new Project(2, "Web-budget"));
        assertThat(actual, contains(expected));
    }
}