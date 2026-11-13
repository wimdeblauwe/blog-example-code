package com.wimdeblauwe.examples.spendleaf.project.repository;

import com.wimdeblauwe.examples.spendleaf.project.Project;
import com.wimdeblauwe.examples.spendleaf.project.ProjectId;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

interface SpringDataJpaProjectRepository extends ListCrudRepository<Project, ProjectId>,
        ListPagingAndSortingRepository<Project, ProjectId> {
}
