package com.wimdeblauwe.examples.transactional_outbox_spring_modulith.order.repository;

import com.wimdeblauwe.examples.transactional_outbox_spring_modulith.order.Order;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, Long> {

}
