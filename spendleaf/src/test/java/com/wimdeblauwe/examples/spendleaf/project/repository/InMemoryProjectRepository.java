package com.wimdeblauwe.examples.spendleaf.project.repository;

import com.wimdeblauwe.examples.spendleaf.project.Project;
import com.wimdeblauwe.examples.spendleaf.project.ProjectId;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * In-memory implementation for use case and controller tests.
 */
public class InMemoryProjectRepository implements ProjectRepository {
    private final Map<ProjectId, Project> values = new LinkedHashMap<>();

    @Override
    public ProjectId nextId() {
        return new ProjectId(UUID.randomUUID());
    }

    @Override
    public void save(Project project) {
        values.put(project.getId(), project);
    }

    @Override
    public List<Project> findAll() {
        return values.values().stream()
                .sorted(Comparator.comparing(Project::getName))
                .toList();
    }

    @Override
    public void deleteAll() {
        values.clear();
    }
}
