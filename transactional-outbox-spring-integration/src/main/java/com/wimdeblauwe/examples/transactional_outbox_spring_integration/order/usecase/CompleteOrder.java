package com.wimdeblauwe.examples.transactional_outbox_spring_integration.order.usecase;

import com.wimdeblauwe.examples.transactional_outbox_spring_integration.infrastructure.mail.MailGateway;
import com.wimdeblauwe.examples.transactional_outbox_spring_integration.infrastructure.mail.MailMessage;
import com.wimdeblauwe.examples.transactional_outbox_spring_integration.order.Order;
import com.wimdeblauwe.examples.transactional_outbox_spring_integration.order.repository.OrderRepository;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CompleteOrder {

  private static final Logger LOGGER = LoggerFactory.getLogger(CompleteOrder.class);
  private final OrderRepository orderRepository;
  private final MailGateway mailGateway;

  public CompleteOrder(OrderRepository orderRepository, MailGateway mailGateway) {
    this.orderRepository = orderRepository;
    this.mailGateway = mailGateway;
  }

  public void execute(BigDecimal amount, String email) {
    LOGGER.info("Completing order for {}", email);
    Order order = new Order();
    order.setAmount(amount);
    order.setCustomerEmail(email);

    LOGGER.info("Save order in database");
    orderRepository.save(order);

    MailMessage message = new MailMessage("Order %s completed".formatted(order.getId()),
        "Your order is registered in our system and will be processed.",
        order.getCustomerEmail());
    LOGGER.info("Sending email for order");
    mailGateway.sendMail(message);
  }
}
