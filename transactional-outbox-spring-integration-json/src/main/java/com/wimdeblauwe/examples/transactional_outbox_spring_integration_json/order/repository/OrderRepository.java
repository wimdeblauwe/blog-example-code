package com.wimdeblauwe.examples.transactional_outbox_spring_integration_json.order.repository;

import com.wimdeblauwe.examples.transactional_outbox_spring_integration_json.order.Order;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, Long> {

}
