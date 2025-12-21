package com.wimdeblauwe.examples.value_objects_presentation;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Embeddable
public record Money(BigDecimal amount, @Enumerated(EnumType.STRING) Currency currency) {

  private static final ConcurrentMap<Currency, Money> cache = new ConcurrentHashMap<>();

  public Money {
    Assert.notNull(amount, "The Money amount should not be null");
    Assert.notNull(currency, "The Money currency should not be null");
    Assert.isTrue(amount.signum() >= 0, "The Money amount should be positive");
  }

  public static Money ofEuro(BigDecimal amount) {
    return new Money(amount, Currency.EUR);
  }

  public static Money ofEuro(int amount) {
    return new Money(BigDecimal.valueOf(amount), Currency.EUR);
  }

  public Money add(Money other) {
    Assert.notNull(other, "Other must not be null");
    Assert.isTrue(this.currency.equals(other.currency),
                  "Currencies must be equal");
    return new Money(this.amount.add(other.amount), this.currency);
  }

  public static Money zero(Currency currency) {
    return cache.computeIfAbsent(currency, c -> new Money(BigDecimal.ZERO, c));
  }
}
