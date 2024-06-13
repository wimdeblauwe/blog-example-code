package com.wimdeblauwe.examples.transactional_outbox_spring_integration.order.repository;

import com.wimdeblauwe.examples.transactional_outbox_spring_integration.order.Order;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, Long> {

}
