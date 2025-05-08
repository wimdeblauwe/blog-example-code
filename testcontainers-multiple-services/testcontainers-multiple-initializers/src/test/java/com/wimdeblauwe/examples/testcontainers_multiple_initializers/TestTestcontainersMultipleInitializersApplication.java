package com.wimdeblauwe.examples.testcontainers_multiple_initializers;

import org.springframework.boot.SpringApplication;

public class TestTestcontainersMultipleInitializersApplication {

	public static void main(String[] args) {
		SpringApplication.from(TestcontainersMultipleInitializersApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
