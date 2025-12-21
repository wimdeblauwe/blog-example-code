package com.wimdeblauwe.examples.value_objects_presentation.repository;

import com.wimdeblauwe.examples.value_objects_presentation.Currency;
import com.wimdeblauwe.examples.value_objects_presentation.Money;
import com.wimdeblauwe.examples.value_objects_presentation.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

  List<Product> findByPriceAmountGreaterThanAndPriceCurrency(
      BigDecimal amount,
      Currency currency);

  @Query("""
      SELECT p FROM Product p WHERE p.price.amount > :#{#money.amount}
      AND p.price.currency = :#{#money.currency}""")
  List<Product> findByPriceGreaterThan(Money money);

  List<Product> findAllByPriceCurrency(Currency currency);
}
