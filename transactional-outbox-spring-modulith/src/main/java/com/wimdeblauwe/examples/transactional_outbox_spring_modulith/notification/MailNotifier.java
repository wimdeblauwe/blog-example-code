package com.wimdeblauwe.examples.transactional_outbox_spring_modulith.notification;

import com.wimdeblauwe.examples.transactional_outbox_spring_modulith.infrastructure.mail.MailMessage;
import com.wimdeblauwe.examples.transactional_outbox_spring_modulith.infrastructure.mail.MailSender;
import com.wimdeblauwe.examples.transactional_outbox_spring_modulith.order.Order;
import com.wimdeblauwe.examples.transactional_outbox_spring_modulith.order.OrderCompleted;
import com.wimdeblauwe.examples.transactional_outbox_spring_modulith.order.repository.OrderRepository;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailNotifier {

  private static final Logger LOGGER = LoggerFactory.getLogger(MailNotifier.class);
  private final MailSender mailSender;
  private final OrderRepository orderRepository;
  private final IncompleteEventPublications incompleteEventPublications;

  public MailNotifier(MailSender mailSender, OrderRepository orderRepository, IncompleteEventPublications incompleteEventPublications) {
    this.mailSender = mailSender;
    this.orderRepository = orderRepository;
    this.incompleteEventPublications = incompleteEventPublications;
  }

  @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
  public void retries() {
    this.incompleteEventPublications.resubmitIncompletePublicationsOlderThan(Duration.ofSeconds(5));
  }

  @ApplicationModuleListener
  public void onOrderCompleted(OrderCompleted orderCompleted) {
    Order order = orderRepository.findById(orderCompleted.orderId())
        .orElseThrow(() -> new RuntimeException("Order not found"));

    MailMessage message = new MailMessage("Order %s completed".formatted(order.getId()),
        "Your order is registered in our system and will be processed.",
        order.getCustomerEmail());
    LOGGER.info("Sending email for order {}", orderCompleted.orderId());
    mailSender.sendMail(message);
  }
}
