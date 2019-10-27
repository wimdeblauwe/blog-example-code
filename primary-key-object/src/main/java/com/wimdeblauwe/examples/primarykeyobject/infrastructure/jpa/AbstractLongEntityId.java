package com.wimdeblauwe.examples.primarykeyobject.infrastructure.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractLongEntityId implements EntityId<Long> {
    @Column(name = "id")
    private final Long value;

    public AbstractLongEntityId(Long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public String asString() {
        return String.valueOf(value);
    }
}
