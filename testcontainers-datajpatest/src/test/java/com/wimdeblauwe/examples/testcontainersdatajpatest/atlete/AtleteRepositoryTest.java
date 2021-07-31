package com.wimdeblauwe.examples.testcontainersdatajpatest.atlete;

import com.wimdeblauwe.examples.testcontainersdatajpatest.infrastructure.test.DatabaseBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AtleteRepositoryTest extends DatabaseBaseTest {


    @Autowired
    private AtleteRepository repository;

    @Test
    void testSave() {
        repository.save(new Atlete("Wout Van Aert", 0, 1, 0));

        assertThat(repository.count()).isEqualTo(1);
    }
}
