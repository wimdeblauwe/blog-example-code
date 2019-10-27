package com.wimdeblauwe.examples.primarykeyobject;


import com.wimdeblauwe.examples.primarykeyobject.order.Order;
import com.wimdeblauwe.examples.primarykeyobject.order.OrderId;
import com.wimdeblauwe.examples.primarykeyobject.order.OrderRepository;
import com.wimdeblauwe.examples.primarykeyobject.user.User;
import com.wimdeblauwe.examples.primarykeyobject.user.UserId;
import com.wimdeblauwe.examples.primarykeyobject.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MultiRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Sql(statements = "CREATE SEQUENCE IF NOT EXISTS HIBERNATE_SEQUENCE;CREATE SEQUENCE IF NOT EXISTS USER_SEQUENCE")
    public void testSaveUser() {
        User user = userRepository.save(new User("Wim"));
        Order order = orderRepository.save(new Order("ABC"));

        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull()
                                .isInstanceOf(UserId.class);
        assertThat(user.getId().getValue()).isPositive();

        assertThat(order).isNotNull();
        assertThat(order.getId()).isNotNull()
                                 .isInstanceOf(OrderId.class);
        assertThat(order.getId().getValue()).isPositive();
    }
}
