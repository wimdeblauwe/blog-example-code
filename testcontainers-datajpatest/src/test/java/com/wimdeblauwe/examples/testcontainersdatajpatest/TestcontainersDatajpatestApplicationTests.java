package com.wimdeblauwe.examples.testcontainersdatajpatest;

import com.wimdeblauwe.examples.testcontainersdatajpatest.atlete.Atlete;
import com.wimdeblauwe.examples.testcontainersdatajpatest.atlete.AtleteRepository;
import com.wimdeblauwe.examples.testcontainersdatajpatest.team.Team;
import com.wimdeblauwe.examples.testcontainersdatajpatest.team.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class TestcontainersDatajpatestApplicationTests {
    @Container
    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>("postgres:11");

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
    }

    @Autowired
    private AtleteRepository atleteRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Test
    void contextLoads() {
        atleteRepository.save(new Atlete("Wout Van Aert", 0, 1, 0));
        assertThat(atleteRepository.count()).isEqualTo(1);

        teamRepository.save(new Team("Team Belgium"));
        assertThat(teamRepository.count()).isEqualTo(1);
    }

}
