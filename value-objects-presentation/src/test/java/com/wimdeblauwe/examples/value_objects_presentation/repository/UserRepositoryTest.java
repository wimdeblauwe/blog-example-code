package com.wimdeblauwe.examples.value_objects_presentation.repository;

import com.wimdeblauwe.examples.value_objects_presentation.Email;
import com.wimdeblauwe.examples.value_objects_presentation.TestcontainersConfiguration;
import com.wimdeblauwe.examples.value_objects_presentation.User;
import com.wimdeblauwe.examples.value_objects_presentation.UserId;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class UserRepositoryTest {

  @Autowired
  private UserRepository repository;
  @Autowired
  private EntityManager entityManager;

  @Test
  void saveSingleUser() {
    User user = new User(UserId.create(), "Wim", Email.of("wim.deblauwe@gmail.com"));
    User savedUser = repository.save(user);

    entityManager.flush();

    assertThat(savedUser)
        .isNotNull();
    assertThat(savedUser.getId())
        .isNotNull();
    assertThat(savedUser.getName())
        .isEqualTo("Wim");
    assertThat(savedUser.getEmail())
        .isEqualTo(Email.of("wim.deblauwe@gmail.com"));
  }

}