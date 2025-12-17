package com.wimdeblauwe.examples.value_objects_presentation.repository;

import com.wimdeblauwe.examples.value_objects_presentation.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
