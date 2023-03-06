package com.wimdeblauwe.examples.viewcomponentdemo.product;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProductService {
    private final Map<Long, Product> products = new HashMap<>();

    public ProductService() {
        List<Product> list = List.of(new Product(1L, "iPhone", "Deepblue"),
                                     new Product(2L, "MacBook Pro", "Spacegray"));
        products.putAll(list.stream().collect(Collectors.toMap(Product::id, product -> product)));
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }

    public Product getProduct(Long productId) {
        return products.get(productId);
    }
}
