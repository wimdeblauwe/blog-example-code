package com.wimdeblauwe.examples.springboottestslices.infrastructure.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TestConfiguration
public class MyAppDataJpaTestConfiguration {
    @Bean
    public ExecutorService executorService() {
        return Executors.newSingleThreadExecutor();
    }
}
