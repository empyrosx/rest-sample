package com.github.empyrosx.rest;

import com.github.empyrosx.rest.model.Project;
import com.github.empyrosx.sample.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by empyros on 26.08.17.
 */
@Path("/projects")
@Api(value = "projects")
public class ProjectController {

    @Inject
    private ProjectService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Gets all projects",
            response = Project.class,
            responseContainer = "List")
    public List<Project> getAll() {
        return service.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Adds new project",
            response = Project.class,
            responseContainer = "List")
    public void add(Project project) {
        service.add(project);
    }
}
