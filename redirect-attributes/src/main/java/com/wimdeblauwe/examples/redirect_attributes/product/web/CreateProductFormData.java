package com.wimdeblauwe.examples.redirect_attributes.product.web;

import com.wimdeblauwe.examples.redirect_attributes.product.Product;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateProductFormData {
  private String name;
  private BigDecimal price;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Product toProduct() {
    return new Product(UUID.randomUUID(), name, price);
  }
}
