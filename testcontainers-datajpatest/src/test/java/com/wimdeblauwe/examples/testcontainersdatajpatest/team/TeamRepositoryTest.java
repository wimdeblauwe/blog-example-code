package com.wimdeblauwe.examples.testcontainersdatajpatest.team;

import com.wimdeblauwe.examples.testcontainersdatajpatest.infrastructure.test.DatabaseBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeamRepositoryTest extends DatabaseBaseTest {

    @Autowired
    private TeamRepository repository;

    @Test
    void testSave() {
        repository.save(new Team("Team Belgium"));

        assertThat(repository.count()).isEqualTo(1);
    }
}
