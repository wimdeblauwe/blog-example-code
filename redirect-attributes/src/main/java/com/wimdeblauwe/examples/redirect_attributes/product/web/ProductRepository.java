package com.wimdeblauwe.examples.redirect_attributes.product.web;

import com.wimdeblauwe.examples.redirect_attributes.product.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

  private final Map<UUID, Product> products = new HashMap<>();

  public void save(Product product) {
    products.put(product.id(), product);
  }

  public List<Product> findAll() {
    return new ArrayList<>(products.values());
  }
}
