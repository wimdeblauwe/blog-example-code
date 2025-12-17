package com.wimdeblauwe.examples.value_objects_presentation;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Embeddable
public record Money(BigDecimal amount, @Enumerated(EnumType.STRING) Currency currency) {

  public Money {
    Assert.notNull(amount, "The Money amount should not be null");
    Assert.notNull(currency, "The Money currency should not be null");
    Assert.isTrue(amount.signum() >= 0, "The Money amount should be positive");
  }

}
