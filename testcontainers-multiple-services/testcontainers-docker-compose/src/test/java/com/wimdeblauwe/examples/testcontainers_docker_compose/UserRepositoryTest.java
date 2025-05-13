package com.wimdeblauwe.examples.testcontainers_docker_compose;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@MyAppDataJpaTest
class UserRepositoryTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);
  @Autowired
  private UserRepository repository;

  @Autowired
  private TestEntityManager entityManager;

//  @MockitoBean // This forces a new Spring test context so that containers need to be started again
//  private JpaRepository dummy;

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