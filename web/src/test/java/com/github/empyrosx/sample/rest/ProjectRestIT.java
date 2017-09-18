package com.github.empyrosx.sample.rest;

import com.github.empyrosx.sample.model.Project;
import com.github.empyrosx.sample.service.ProjectService;
import com.github.empyrosx.sample.testing.rest.JaxrsRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static com.github.empyrosx.sample.testing.rest.engine.ResteasyResponceMatchers.*;
import static org.mockito.Mockito.doReturn;

public class ProjectRestIT {

    @Mock
    private ProjectService service;

    @Rule
    public JaxrsRule rule = new JaxrsRule(ProjectController.class);

    @Before
    public void setUp() throws Exception {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "Web-consolidation"));
        projects.add(new Project(2L, "Web-planning"));
        doReturn(projects).when(service).getAll();
    }

    @Test
    public void testProject() throws Exception {
        List<Project> expected = new ArrayList<>();
        expected.add(new Project(1L, "Web-consolidation"));
        expected.add(new Project(2L, "Web-planning"));

        rule.jsonRequest("/projects").get()
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_TYPE))
                .andExpect(json().listEqualTo(expected, Project.class));
    }

    @Test
    public void testOneProject() throws Exception {
        Project expected = new Project(1L, "Web-cons");

        rule.jsonRequest("/projects/one").get()
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_TYPE))
                .andExpect(json().equalTo(expected));
    }

}

