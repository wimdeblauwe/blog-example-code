package com.wimdeblauwe.examples.value_objects_presentation;

import jakarta.persistence.*;

@Entity
public class Product {

  @Id
  private Long id;

  private String name;

  @Embedded
  private Money price;

  @Embedded
  @AttributeOverride(name = "amount", column = @Column(name = "material_cost_amount"))
  @AttributeOverride(name = "currency", column = @Column(name = "material_cost_currency"))
  private Money materialCost;

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
