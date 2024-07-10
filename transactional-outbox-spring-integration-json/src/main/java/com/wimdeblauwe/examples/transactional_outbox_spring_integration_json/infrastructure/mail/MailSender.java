package com.wimdeblauwe.examples.transactional_outbox_spring_integration_json.infrastructure.mail;

public interface MailSender {

  void sendMail(MailMessage mailMessage);
}
