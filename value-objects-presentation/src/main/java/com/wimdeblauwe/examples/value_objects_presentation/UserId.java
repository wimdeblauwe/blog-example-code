package com.wimdeblauwe.examples.value_objects_presentation;

import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

import java.util.UUID;

@Embeddable
public record UserId(UUID value) {
    public UserId {
        Assert.notNull(value, "Value must not be null");
    }

    public static UserId create() {
        return new UserId(UUID.randomUUID());
    }
}