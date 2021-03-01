package com.wimdeblauwe.examples.attributeconvertervsembeddable.attributeconverter;

import org.springframework.util.Assert;

public class Email {
    private final String value;

    public Email(String value) {
        Assert.hasText(value, "value should have text");
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
