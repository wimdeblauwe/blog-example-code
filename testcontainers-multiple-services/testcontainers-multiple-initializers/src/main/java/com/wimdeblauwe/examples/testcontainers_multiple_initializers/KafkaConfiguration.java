package com.wimdeblauwe.examples.testcontainers_multiple_initializers;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

  @Bean
  public NewTopic bicycleCreatedTopic() {
    return TopicBuilder.name("bicycle-created").build();
  }
}
