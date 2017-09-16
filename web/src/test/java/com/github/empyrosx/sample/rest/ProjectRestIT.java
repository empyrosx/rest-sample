package com.github.empyrosx.sample.rest;

import com.github.empyrosx.sample.model.Project;
import com.github.empyrosx.sample.service.ProjectService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

public class ProjectRestIT extends BaseRestTest {

    @Mock
    private ProjectService service;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "Web-consolidation"));
        projects.add(new Project(2L, "Web-planning"));
        doReturn(projects).when(service).getAll();
    }

    @Override
    protected Class[] getControllers() {
        return new Class[]{ProjectController.class};
    }

    @Test
    public void testProject() throws Exception {
        String actual = jsonRequest("/projects").get();
        System.out.println(actual);
    }
}

