package com.github.empyrosx.sample.service;

import com.github.empyrosx.sample.rest.model.Project;

import java.util.List;

/**
 * Created by empyros on 26.08.17.
 */
public interface ProjectService {

    List<Project> getAll();

    void add(Project project);
}
