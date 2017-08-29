package com.github.empyrosx.sample.service;

import com.github.empyrosx.sample.rest.model.Project;
import com.github.empyrosx.sample.repository.ProjectRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by empyros on 26.08.17.
 */
@ApplicationScoped
public class ProjectServiceImpl implements ProjectService {

    @Inject
    private ProjectRepository repository;

    public List<Project> getAll() {
        return repository.findAll();
    }

    public void add(Project project) {
        repository.save(project);
    }
}
