package com.wimdeblauwe.examples.testcontainersdatajpatest.infrastructure.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

public class DatabaseBaseTest {
    protected static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>("postgres:11");

    @BeforeAll
    static void start() {
        CONTAINER.start();
    }

    @AfterAll
    static void stop() {
        CONTAINER.stop();
    }
}
