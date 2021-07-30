package com.wimdeblauwe.examples.testcontainersdatajpatest.infrastructure.test;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLExtension implements BeforeAllCallback, AfterAllCallback {

    private PostgreSQLContainer<?> postgres;

    @Override
    public void beforeAll(ExtensionContext context) {
        postgres = new PostgreSQLContainer<>("postgres:11");

        postgres.start();
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
    }

    @Override
    public void afterAll(ExtensionContext context) {
        postgres.stop();
    }
}
