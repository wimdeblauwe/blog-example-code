package com.wimdeblauwe.examples.testcontainers_multiple_initializers;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@MyAppSpringBootTest
class CreateBicycleUseCaseTest {

  @Autowired
  private CreateBicycleUseCase useCase;

  @Autowired
  private Consumer<String, String> consumer;

  @Test
  void testExecute() {
    useCase.execute("My first bicycle");

    ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "bicycle-created");
    assertThat(singleRecord).isNotNull();
    assertThat(singleRecord.value()).isEqualTo("My first bicycle");
  }
}