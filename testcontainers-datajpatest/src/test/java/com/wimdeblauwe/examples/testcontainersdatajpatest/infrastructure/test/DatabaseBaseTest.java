package com.wimdeblauwe.examples.testcontainersdatajpatest.infrastructure.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class DatabaseBaseTest {
    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>("postgres:11");

    @BeforeAll
    static void start() {
        CONTAINER.start();
    }

    @AfterAll
    static void stop() {
        CONTAINER.stop();
    }

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
}
