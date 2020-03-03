package com.wimdeblauwe.examples.valueobjectswithrestapiuuid.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.StringJoiner;
import java.util.UUID;

public class UserId {
    private UUID id;

    public UserId(UUID id) {
        this.id = id;
    }

    @JsonValue
    public UUID getId() {
        return id;
    }

    @JsonCreator
    public static UserId fromString(String id) {
        return new UserId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserId.class.getSimpleName() + "[", "]")
                .add(String.format("id=%s", id))
                .toString();
    }
}
