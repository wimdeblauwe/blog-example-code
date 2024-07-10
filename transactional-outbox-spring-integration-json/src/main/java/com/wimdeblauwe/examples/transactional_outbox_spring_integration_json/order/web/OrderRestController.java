package com.wimdeblauwe.examples.transactional_outbox_spring_integration_json.order.web;

import com.wimdeblauwe.examples.transactional_outbox_spring_integration_json.order.usecase.CompleteOrder;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

  private final CompleteOrder completeOrder;

  public OrderRestController(CompleteOrder completeOrder) {
    this.completeOrder = completeOrder;
  }

  @PostMapping
  public void completeOrder(@RequestBody CompleteOrderRequest request) {
    completeOrder.execute(request.amount(), request.email());
  }

  public record CompleteOrderRequest(BigDecimal amount, String email) {

  }
}
