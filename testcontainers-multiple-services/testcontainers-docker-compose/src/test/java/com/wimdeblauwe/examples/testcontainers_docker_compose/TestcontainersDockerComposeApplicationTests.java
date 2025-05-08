package com.wimdeblauwe.examples.testcontainers_docker_compose;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TestcontainersDockerComposeApplicationTests {

	@Test
	void contextLoads() {
	}

}
