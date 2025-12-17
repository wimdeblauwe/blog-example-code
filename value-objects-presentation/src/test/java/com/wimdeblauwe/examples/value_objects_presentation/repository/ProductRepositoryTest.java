package com.wimdeblauwe.examples.value_objects_presentation.repository;

import com.wimdeblauwe.examples.value_objects_presentation.Money;
import com.wimdeblauwe.examples.value_objects_presentation.Currency;
import com.wimdeblauwe.examples.value_objects_presentation.Product;
import com.wimdeblauwe.examples.value_objects_presentation.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class ProductRepositoryTest {

  @Autowired
  private ProductRepository repository;

  @Test
  void saveSingleProduct() {
    Money price = new Money(new BigDecimal("19.99"), Currency.EUR);
    Product product = new Product(1L, "Test Product", price);

    Product saved = repository.save(product);

    assertThat(saved.getId()).isEqualTo(1L);
    assertThat(saved.getName()).isEqualTo("Test Product");
    assertThat(saved.getPrice()).isEqualTo(price);
    assertThat(repository.findById(1L)).isPresent();
  }

  @Test
  void saveMultipleProductsAndFindAll() {
    Product product1 = new Product(1L, "Product One", new Money(new BigDecimal("10.00"), Currency.EUR));
    Product product2 = new Product(2L, "Product Two", new Money(new BigDecimal("20.00"), Currency.USD));
    Product product3 = new Product(3L, "Product Three", new Money(new BigDecimal("30.00"), Currency.EUR));

    repository.save(product1);
    repository.save(product2);
    repository.save(product3);

    List<Product> allProducts = (List<Product>) repository.findAll();

    assertThat(allProducts).hasSize(3);
    assertThat(allProducts).extracting(Product::getName)
        .containsExactlyInAnyOrder("Product One", "Product Two", "Product Three");
  }
}
