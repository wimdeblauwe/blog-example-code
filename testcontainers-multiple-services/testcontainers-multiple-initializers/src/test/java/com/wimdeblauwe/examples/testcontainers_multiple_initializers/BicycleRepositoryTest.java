package com.wimdeblauwe.examples.testcontainers_multiple_initializers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DataJpaTestcontainersConfiguration.class)
class BicycleRepositoryTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(BicycleRepositoryTest.class);

  @Autowired
  private BicycleRepository repository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  void testSave() {
    LOGGER.info("Starting testSave");
    UUID id = UUID.randomUUID();
    repository.save(new Bicycle(id, "My first bicycle"));

    entityManager.flush();
    entityManager.clear();

    Bicycle bicycle = repository.findById(id).orElseThrow();
    assertThat(bicycle).isNotNull();
    assertThat(bicycle.getName()).isEqualTo("My first bicycle");
  }
}