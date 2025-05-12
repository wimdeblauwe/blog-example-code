package com.wimdeblauwe.examples.testcontainers_multiple_initializers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional
public class CreateBicycleUseCase {

  private final BicycleRepository bicycleRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public CreateBicycleUseCase(BicycleRepository bicycleRepository,
                              KafkaTemplate<String, String> kafkaTemplate) {
    this.bicycleRepository = bicycleRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void execute(String name) {
    bicycleRepository.save(new Bicycle(UUID.randomUUID(), name));

    kafkaTemplate.send("bicycle-created", name);
  }
}
