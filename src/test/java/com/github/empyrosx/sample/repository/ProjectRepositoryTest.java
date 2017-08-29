package com.github.empyrosx.sample.repository;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.empyrosx.sample.rest.model.Project;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

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
        flyway = new Flyway();
        flyway.setDataSource(DB_URL, "sa", "");
        flyway.setLocations("db/migration");
        flyway.setValidateOnMigrate(true);
        flyway.migrate();

        connection = flyway.getDataSource().getConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.addBatch("INSERT INTO PUBLIC.project(id, name) VALUES (1, 'Erich')");
            stmt.addBatch("INSERT INTO PUBLIC.project(id, name) VALUES (2, 'Richard')");
            int[] result = stmt.executeBatch();
            assertEquals(result.length, 2);
        }
    }

    @Test
    @DataSet(value = "all-projects.xml")
    public void testGetAllProjects() throws Exception {
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("select * from project");
//        System.out.println(resultSet.getString(1));

        Query q = entityManager.createNativeQuery("select count(*) from project");
        int result = q.getFirstResult();
        System.out.println(result);


        System.out.println(entityManager);
        System.out.println(repository);

        List<Project> actual = repository.findAll();
        assertEquals(2, actual.size());
    }
}