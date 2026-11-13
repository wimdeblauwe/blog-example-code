package com.wimdeblauwe.examples.spendleaf.project.usecase;

import com.wimdeblauwe.examples.spendleaf.infrastructure.stereotype.UseCase;
import com.wimdeblauwe.examples.spendleaf.project.Project;
import com.wimdeblauwe.examples.spendleaf.project.repository.ProjectRepository;

import java.util.List;

@UseCase(readOnly = true)
public class GetProjects {
    private final ProjectRepository repository;

    public GetProjects(ProjectRepository repository) {
        this.repository = repository;
    }

    public List<Project> execute() {
        return repository.findAll();
    }
}
