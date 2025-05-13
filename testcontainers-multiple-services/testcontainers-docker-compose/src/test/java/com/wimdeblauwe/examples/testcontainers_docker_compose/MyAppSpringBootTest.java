package com.wimdeblauwe.examples.testcontainers_docker_compose;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@ContextConfiguration(initializers = IntegrationTestInitializer.class)
@Import(IntegrationTestConfiguration.class)
public @interface MyAppSpringBootTest {
}
