package com.wimdeblauwe.examples.value_objects_presentation;

import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

import java.util.UUID;

@Embeddable
public record OrderId(UUID value) {
    public OrderId {
        Assert.notNull(value, "Value must not be null");
    }

    public static OrderId create() {
        return new OrderId(UUID.randomUUID());
    }
}