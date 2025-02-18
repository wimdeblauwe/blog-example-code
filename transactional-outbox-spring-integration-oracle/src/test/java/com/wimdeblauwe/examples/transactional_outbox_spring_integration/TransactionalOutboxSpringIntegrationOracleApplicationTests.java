package com.wimdeblauwe.examples.transactional_outbox_spring_integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TransactionalOutboxSpringIntegrationOracleApplicationTests {

	@Test
	void contextLoads() {
	}

}
