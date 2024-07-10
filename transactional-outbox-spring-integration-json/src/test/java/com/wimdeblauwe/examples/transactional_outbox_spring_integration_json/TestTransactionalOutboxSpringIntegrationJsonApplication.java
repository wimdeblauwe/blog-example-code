package com.wimdeblauwe.examples.transactional_outbox_spring_integration_json;

import org.springframework.boot.SpringApplication;

public class TestTransactionalOutboxSpringIntegrationJsonApplication {

	public static void main(String[] args) {
		SpringApplication.from(TransactionalOutboxSpringIntegrationJsonApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
