package com.wimdeblauwe.examples.value_objects_presentation.web;

import com.wimdeblauwe.examples.value_objects_presentation.Money;
import com.wimdeblauwe.examples.value_objects_presentation.Product;

public record ProductResponse(Long id, String name, Money price) {
  public static ProductResponse of(Product product) {
    return new ProductResponse(product.getId(), product.getName(), product.getPrice());
  }
}
