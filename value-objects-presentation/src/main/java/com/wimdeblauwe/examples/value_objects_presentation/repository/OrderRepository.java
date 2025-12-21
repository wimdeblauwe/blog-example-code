package com.wimdeblauwe.examples.value_objects_presentation.repository;

import com.wimdeblauwe.examples.value_objects_presentation.Order;
import com.wimdeblauwe.examples.value_objects_presentation.OrderId;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, OrderId> {
}
