package com.wimdeblauwe.examples.transactional_outbox_spring_modulith.order.usecase;

import com.wimdeblauwe.examples.transactional_outbox_spring_modulith.infrastructure.mail.MailMessage;
import com.wimdeblauwe.examples.transactional_outbox_spring_modulith.order.Order;
import com.wimdeblauwe.examples.transactional_outbox_spring_modulith.order.OrderCompleted;
import com.wimdeblauwe.examples.transactional_outbox_spring_modulith.order.repository.OrderRepository;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CompleteOrder {

  private static final Logger LOGGER = LoggerFactory.getLogger(CompleteOrder.class);
  private final OrderRepository orderRepository;
  private final ApplicationEventPublisher eventPublisher;

  public CompleteOrder(OrderRepository orderRepository, ApplicationEventPublisher eventPublisher) {
    this.orderRepository = orderRepository;
    this.eventPublisher = eventPublisher;
  }

  public void execute(BigDecimal amount, String email) {
    LOGGER.info("Completing order for {}", email);
    Order order = new Order();
    order.setAmount(amount);
    order.setCustomerEmail(email);

    LOGGER.info("Save order in database");
    orderRepository.save(order);

    eventPublisher.publishEvent(new OrderCompleted(order.getId()));
  }
}
