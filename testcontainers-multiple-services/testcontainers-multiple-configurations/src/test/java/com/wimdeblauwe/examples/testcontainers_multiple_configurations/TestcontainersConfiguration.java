package com.wimdeblauwe.examples.testcontainers_multiple_configurations;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

  public static final int SCHEMA_REGISTRY_PORT = 8081;

  @Bean
  public Network network() {
    return Network.newNetwork();
  }

  @Bean
  @ServiceConnection
  ConfluentKafkaContainer kafkaContainer(Network network) {
    return new ConfluentKafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
        .withNetworkAliases("kafka")
        .withNetwork(network)
        /*.withReuse(true)*/;
  }

  @Bean
  GenericContainer<?> schemaRegistryContainer(ConfluentKafkaContainer kafkaContainer,
                                              Network network) {
    return new GenericContainer<>(DockerImageName.parse("confluentinc/cp-schema-registry:latest"))
        .withNetwork(network)
        .dependsOn(kafkaContainer)
        .withExposedPorts(SCHEMA_REGISTRY_PORT)
        .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
        .withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:" + SCHEMA_REGISTRY_PORT)
        .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "PLAINTEXT://" + kafkaContainer.getNetworkAliases().getFirst() + ":9093")
        .withEnv("SCHEMA_REGISTRY_KAFKASTORE_SECURITY_PROTOCOL", "PLAINTEXT")
        .waitingFor(Wait.forHttp("/subjects").forStatusCode(200).withStartupTimeout(Duration.ofSeconds(10)));
  }

  @Bean
  public DynamicPropertyRegistrar schemaRegistryProperties(GenericContainer<?> schemaRegistryContainer) {
    return (properties) -> {
      properties.add("spring.kafka.properties.schema.registry.url", () -> "http://" + schemaRegistryContainer.getHost() + ":" + schemaRegistryContainer.getMappedPort(SCHEMA_REGISTRY_PORT));
    };
  }

  @Bean
  public Consumer<String, String> testConsumer(ConfluentKafkaContainer kafka) {
    Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
        kafka.getBootstrapServers(),
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

  @Bean
  @ServiceConnection
  PostgreSQLContainer<?> postgresContainer() {
    return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
        /*.withReuse(true)*/;
  }

}
