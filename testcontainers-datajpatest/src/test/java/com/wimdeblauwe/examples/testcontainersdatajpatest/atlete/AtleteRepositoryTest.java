package com.wimdeblauwe.examples.testcontainersdatajpatest.atlete;

import com.wimdeblauwe.examples.testcontainersdatajpatest.infrastructure.test.DatabaseBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AtleteRepositoryTest extends DatabaseBaseTest {

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> {
            String jdbcUrl = CONTAINER.getJdbcUrl();
            System.out.println("jdbcUrl = " + jdbcUrl);
            return jdbcUrl;
        });
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
    }

    @Autowired
    private AtleteRepository repository;

    @Test
    void testSave() {
        repository.save(new Atlete("Wout Van Aert", 0, 1, 0));

        assertThat(repository.count()).isEqualTo(1);
    }
}
