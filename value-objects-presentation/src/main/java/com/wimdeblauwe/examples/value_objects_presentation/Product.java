package com.wimdeblauwe.examples.value_objects_presentation;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {

  @Id
  private Long id;

  private String name;

  @Embedded
  private Money price;

  protected Product() {
  }

  public Product(Long id, String name, Money price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Money getPrice() {
    return price;
  }
}
