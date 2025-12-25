package com.wimdeblauwe.examples.value_objects_presentation.web;

import com.wimdeblauwe.examples.value_objects_presentation.Product;
import com.wimdeblauwe.examples.value_objects_presentation.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

  private final ProductRepository repository;

  public ProductRestController(ProductRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public Page<ProductResponse> getProducts(Pageable pageable) {
    return repository.findAll(pageable)
        .map(ProductResponse::of);
  }

  @PostMapping
  public ProductResponse createProduct(@RequestBody CreateProductRequest request) {
    Product product = repository.save(new Product(null,
                                                  request.name(),
                                                  request.price()));
    return ProductResponse.of(product);
  }
}
