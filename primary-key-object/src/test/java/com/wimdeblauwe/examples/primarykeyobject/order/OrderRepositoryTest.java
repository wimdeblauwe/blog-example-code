package com.wimdeblauwe.examples.primarykeyobject.order;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Test
    @Sql(statements = "CREATE SEQUENCE IF NOT EXISTS HIBERNATE_SEQUENCE")
    public void testSaveUser() {
        Order order = repository.save(new Order("ABC"));

        assertThat(order).isNotNull();
        assertThat(order.getId()).isNotNull()
                                 .isInstanceOf(OrderId.class);
        assertThat(order.getId().getValue()).isPositive();
    }
}
