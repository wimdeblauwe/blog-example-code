package com.wimdeblauwe.examples.transactional_outbox_spring_integration;

import org.springframework.boot.SpringApplication;

public class TestTransactionalOutboxSpringIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.from(TransactionalOutboxSpringIntegrationApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
