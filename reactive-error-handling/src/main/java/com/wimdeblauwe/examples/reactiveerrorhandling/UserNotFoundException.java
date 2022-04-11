package com.wimdeblauwe.examples.reactiveerrorhandling;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private final Long userId;

    public UserNotFoundException(Long userId) {
        super("No user found for id " + userId);
        this.userId = userId;
    }

    @ResponseErrorProperty
    public Long getUserId() {
        return userId;
    }
}
