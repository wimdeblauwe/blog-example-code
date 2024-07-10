package com.wimdeblauwe.examples.transactional_outbox_spring_integration_json.infrastructure.mail;

import java.io.Serial;
import java.io.Serializable;

public record MailMessage(String subject, String body, String to) implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
}
