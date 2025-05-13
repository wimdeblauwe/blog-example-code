package com.wimdeblauwe.examples.testcontainers_docker_compose;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.testcontainers.kafka.ConfluentKafkaContainer;

import java.util.List;
import java.util.Map;

@TestConfiguration(proxyBeanMethods = false)
public class IntegrationTestConfiguration {
  @Bean
  public Consumer<String, String> testConsumer() {
    Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
        "localhost:9092",
        "testGroup",
        "true");
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    Consumer<String, String> consumer = new DefaultKafkaConsumerFactory<>(
        consumerProps,
        new StringDeserializer(),
        new StringDeserializer())
        .createConsumer();
    consumer.subscribe(List.of("bicycle-created"));
    return consumer;
  }

}
