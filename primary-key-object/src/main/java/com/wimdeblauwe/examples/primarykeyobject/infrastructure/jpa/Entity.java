package com.wimdeblauwe.examples.primarykeyobject.infrastructure.jpa;

public interface Entity<T extends EntityId> {
    T getId();
}
