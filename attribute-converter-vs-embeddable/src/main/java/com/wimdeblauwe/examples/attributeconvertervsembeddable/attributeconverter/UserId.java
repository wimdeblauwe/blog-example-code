package com.wimdeblauwe.examples.attributeconvertervsembeddable.attributeconverter;

import javax.persistence.Column;
import java.io.Serializable;


public class UserId implements Serializable {
    @Column(name = "id")
    private Long value;

    protected UserId() {
    }

    public UserId(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
