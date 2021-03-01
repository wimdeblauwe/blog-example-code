package com.wimdeblauwe.examples.attributeconvertervsembeddable.embeddable;

import org.springframework.util.Assert;

import javax.persistence.Embeddable;

@Embeddable
public class Email {
    private String value;

    protected Email() {
    }

    public Email(String value) {
        Assert.hasText(value, "value should have text");
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
