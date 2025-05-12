package com.wimdeblauwe.examples.testcontainers_multiple_initializers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class UserRepositoryTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);
  @Autowired
  private UserRepository repository;

  @Autowired
  private TestEntityManager entityManager;

  @MockitoBean
  private JpaRepository dummy;

  @Test
  void testSave() {
    LOGGER.info("Starting testSave");
    UUID id = UUID.randomUUID();
    repository.save(new User(id, "Wim"));

    entityManager.flush();
    entityManager.clear();

    User user = repository.findById(id).orElseThrow();
    assertThat(user).isNotNull();
    assertThat(user.getName()).isEqualTo("Wim");
  }
}