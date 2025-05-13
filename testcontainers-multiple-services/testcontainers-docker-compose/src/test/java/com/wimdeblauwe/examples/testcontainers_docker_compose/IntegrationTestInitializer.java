package com.wimdeblauwe.examples.testcontainers_docker_compose;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

public class IntegrationTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final ComposeContainer CONTAINER = new ComposeContainer(new File("compose.yaml"))
      .withExposedService("postgres", 5432)
      .withExposedService("kafka", 9092)
      .withExposedService("schema-registry", 8081)
      .waitingFor("schema-registry", Wait.forHealthcheck())
      .withTailChildContainers(true);

  static {
    CONTAINER.start();
  }

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    TestPropertyValues.of(
        // db properties
        "spring.datasource.url=jdbc:postgresql://%s:%s/mydatabase".formatted(
            CONTAINER.getServiceHost("postgres", 5432),
            CONTAINER.getServicePort("postgres", 5432)),
        "spring.datasource.username=myuser",
        "spring.datasource.password=secret",
        // kafka properties
        "spring.kafka.bootstrap-servers=%s:%s".formatted(
            CONTAINER.getServiceHost("kafka", 9092),
            CONTAINER.getServicePort("kafka", 9092)),
        "spring.kafka.properties.schema.registry.url=http://%s:%s".formatted(
            CONTAINER.getServiceHost("schema-registry", 8081),
            CONTAINER.getServicePort("schema-registry", 8081)
        )
    ).applyTo(applicationContext);
  }
}
