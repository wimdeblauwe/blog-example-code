package com.wimdeblauwe.examples.transactional_outbox_spring_integration;

import org.springframework.boot.SpringApplication;

public class TestTransactionalOutboxSpringIntegrationOracleApplication {

	public static void main(String[] args) {
		SpringApplication.from(TransactionalOutboxSpringIntegrationOracleApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
