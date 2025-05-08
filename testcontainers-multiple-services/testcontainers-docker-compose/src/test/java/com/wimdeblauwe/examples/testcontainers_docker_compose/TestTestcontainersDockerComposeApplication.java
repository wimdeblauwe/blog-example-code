package com.wimdeblauwe.examples.testcontainers_docker_compose;

import org.springframework.boot.SpringApplication;

public class TestTestcontainersDockerComposeApplication {

	public static void main(String[] args) {
		SpringApplication.from(TestcontainersDockerComposeApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
