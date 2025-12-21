package com.wimdeblauwe.examples.value_objects_presentation;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_order")
public class Order {
  @EmbeddedId
  @AttributeOverride(name = "value", column = @Column(name = "id"))
  private OrderId id;

  private LocalDateTime orderDate;

  protected Order() {}

  public Order(OrderId id,
               LocalDateTime orderDate) {
    this.id = id;
    this.orderDate = orderDate;
  }

  public OrderId getId() {
    return id;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }
}
