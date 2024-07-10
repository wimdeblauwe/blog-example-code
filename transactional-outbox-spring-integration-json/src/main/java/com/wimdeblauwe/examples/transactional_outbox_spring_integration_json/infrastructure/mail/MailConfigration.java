package com.wimdeblauwe.examples.transactional_outbox_spring_integration_json.infrastructure.mail;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;

@Configuration
public class MailConfigration {

  @Bean
  public DirectChannel mailInput() {
    return new DirectChannel();
  }

  @Bean
  public QueueChannel mailOutbox(JdbcChannelMessageStore jdbcChannelMessageStore) {
    return MessageChannels.queue(jdbcChannelMessageStore, "mail-outbox").getObject();
  }

  @Bean
  public IntegrationFlow mailFlow(JdbcChannelMessageStore jdbcChannelMessageStore,
      MailSender mailSender) {
    return IntegrationFlow.from(mailInput())
        .channel(mailOutbox(jdbcChannelMessageStore))
        .handle(message -> {
          MailMessage mailMessage = (MailMessage) message.getPayload();
          mailSender.sendMail(mailMessage);
        }, e -> e.poller(Pollers.fixedDelay(Duration.ofSeconds(1))
            .transactional()))
        .get();
  }

}
