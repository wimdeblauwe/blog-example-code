package com.wimdeblauwe.examples.spendleaf.project.repository;

import com.wimdeblauwe.examples.spendleaf.project.Project;
import com.wimdeblauwe.examples.spendleaf.project.ProjectId;

import java.util.List;

public interface ProjectRepository {
    ProjectId nextId();

    void save(Project project);

    /**
     * All projects, sorted by name.
     */
    List<Project> findAll();

    void deleteAll();
}
