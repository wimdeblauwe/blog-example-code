package com.wimdeblauwe.examples.value_objects_presentation.repository;

import com.wimdeblauwe.examples.value_objects_presentation.Order;
import com.wimdeblauwe.examples.value_objects_presentation.OrderId;
import com.wimdeblauwe.examples.value_objects_presentation.TestcontainersConfiguration;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(TestcontainersConfiguration.class)
class OrderRepositoryTest {

  @Autowired
  private OrderRepository repository;
  @Autowired
  private EntityManager entityManager;

  @Test
  void saveSingleOrder() {
    Order order = new Order(OrderId.create(), LocalDateTime.now());

    Order saved = repository.save(order);

    entityManager.flush();
    entityManager.clear();

    assertThat(saved.getId())
        .isNotNull();
    assertThat(saved.getOrderDate())
        .isNotNull();
  }
}