package com.github.empyrosx.sample.repository;

import com.github.empyrosx.rest.model.Project;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * Created by empyros on 26.08.17.
 */
@Repository
public interface ProjectRepository extends EntityRepository<Project, Long>{
}
