package com.wimdeblauwe.examples.spendleaf.project.repository;

import com.wimdeblauwe.examples.spendleaf.project.Project;
import com.wimdeblauwe.examples.spendleaf.project.ProjectId;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
class JpaProjectRepository implements ProjectRepository {
    private final SpringDataJpaProjectRepository repository;

    JpaProjectRepository(SpringDataJpaProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProjectId nextId() {
        return new ProjectId(UUID.randomUUID());
    }

    @Override
    public void save(Project project) {
        repository.save(project);
    }

    @Override
    public List<Project> findAll() {
        return repository.findAll(Sort.by("name"));
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
