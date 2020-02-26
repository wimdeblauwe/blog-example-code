package com.wimdeblauwe.examples.valueobjectswithrestapi.todo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wimdeblauwe.examples.valueobjectswithrestapi.user.UserId;

import java.util.StringJoiner;

public class CreateTodoParameters {
    private final UserId userId;
    private final String description;

    @JsonCreator
    public CreateTodoParameters(@JsonProperty("userId") UserId userId,
                                @JsonProperty("description") String description) {
        this.userId = userId;
        this.description = description;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CreateTodoParameters.class.getSimpleName() + "[", "]")
                .add(String.format("userId=%s", userId))
                .add(String.format("description='%s'", description))
                .toString();
    }
}
