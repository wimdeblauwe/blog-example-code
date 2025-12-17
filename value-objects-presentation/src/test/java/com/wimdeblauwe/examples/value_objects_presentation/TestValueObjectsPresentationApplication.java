package com.wimdeblauwe.examples.value_objects_presentation;

import org.springframework.boot.SpringApplication;

public class TestValueObjectsPresentationApplication {

	public static void main(String[] args) {
		SpringApplication.from(ValueObjectsPresentationApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
