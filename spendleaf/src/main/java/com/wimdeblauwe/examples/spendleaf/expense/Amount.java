package com.wimdeblauwe.examples.spendleaf.expense;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * An amount in euros, always with 2 decimals.
 */
public record Amount(BigDecimal value) {
    public Amount {
        Assert.notNull(value, "The Amount value should not be null");
        Assert.isTrue(value.signum() > 0, "The Amount value should be greater than zero");
        value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public static Amount ofEuros(String euros) {
        return new Amount(new BigDecimal(euros));
    }
}
