package com.wimdeblauwe.examples.spendleaf.project.repository;

import com.wimdeblauwe.examples.spendleaf.infrastructure.test.SpendleafDataJpaTest;
import com.wimdeblauwe.examples.spendleaf.project.Project;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@SpendleafDataJpaTest
class JpaProjectRepositoryTest {

    @Autowired
    private JpaProjectRepository repository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindAll_sortedByName() {
        repository.save(new Project(repository.nextId(), "Cygnus"));
        repository.save(new Project(repository.nextId(), "Atlas"));
        repository.save(new Project(repository.nextId(), "Borealis"));
        entityManager.flush();
        entityManager.clear();

        assertThat(repository.findAll())
                .extracting(Project::getName)
                .containsExactly("Atlas", "Borealis", "Cygnus");
    }
}
