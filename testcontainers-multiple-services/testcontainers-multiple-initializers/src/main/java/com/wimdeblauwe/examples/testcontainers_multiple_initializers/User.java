package com.wimdeblauwe.examples.testcontainers_multiple_initializers;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "\"user\"")
public class User {

  @Id
  private UUID id;

  private String name;

  protected User() {

  }

  public User(UUID id,
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
