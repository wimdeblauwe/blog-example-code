package com.wimdeblauwe.examples.valueobjectswithrestapi.user;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.StringJoiner;

public class UserId {
    private long id;

    public UserId(long id) {
        this.id = id;
    }

    @JsonValue
    public long getId() {
        return id;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", UserId.class.getSimpleName() + "[", "]")
                .add(String.format("id=%s", id))
                .toString();
    }
}
