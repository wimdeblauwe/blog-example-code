package com.wimdeblauwe.examples.testcontainers_multiple_initializers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TestcontainersMultipleInitializersApplicationTests {

	@Test
	void contextLoads() {
	}

}
