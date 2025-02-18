package com.wimdeblauwe.examples.transactional_outbox_spring_integration.infrastructure.mail;

import java.util.random.RandomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingMailSender implements
    MailSender {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingMailSender.class);
  private final RandomGenerator randomGenerator = RandomGenerator.getDefault();

  @Override
  public void sendMail(MailMessage mailMessage) {
    if (randomGenerator.nextBoolean()) {
      LOGGER.info("Sending email: {}", mailMessage);
    } else {
      throw new RuntimeException("Email server down for mail " + mailMessage);
    }
  }
}
