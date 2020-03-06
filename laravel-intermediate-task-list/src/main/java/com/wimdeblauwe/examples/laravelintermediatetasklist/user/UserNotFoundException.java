package com.wimdeblauwe.examples.laravelintermediatetasklist.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
