package com.wimdeblauwe.examples.testcontainers_multiple_initializers;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Bicycle {

  @Id
  private UUID id;

  private String name;

  protected Bicycle() {

  }

  public Bicycle(UUID id,
                 String name) {
    this.id = id;
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
