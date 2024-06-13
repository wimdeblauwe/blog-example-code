package com.wimdeblauwe.examples.transactional_outbox_spring_integration.infrastructure.mail;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MailGateway {

  @Gateway(requestChannel = "mailInput")
  void sendMail(MailMessage mailMessage);
}
