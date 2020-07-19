package com.wimdeblauwe.examples.errorhandling.inforequest;

public class InfoRequest {
    private final Long id;
    private final String name;
    private final String phoneNumber;
    private final String email;

    public InfoRequest(Long id, String name, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
