package com.wimdeblauwe.examples.value_objects_presentation;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.Assert;

public record Email(@JsonValue String value) {
  public Email {
    Assert.notNull(value, "The Email value should not be null");
    Assert.isTrue(value.contains("@"), "The Email value should contain an @");
  }

  public static Email of(String value) {
    return new Email(value);
  }
}
