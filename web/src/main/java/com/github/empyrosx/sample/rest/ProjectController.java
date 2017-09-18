package com.github.empyrosx.sample.rest;

import com.github.empyrosx.sample.model.Project;
import com.github.empyrosx.sample.service.ProjectService;

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
public class ProjectController {

    @Inject
    private ProjectService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> getAll() {
        return service.getAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/one")
    public Project getOne() {
        return new Project(1L, "Web-cons");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(Project project) {
        service.add(project);
    }
}
