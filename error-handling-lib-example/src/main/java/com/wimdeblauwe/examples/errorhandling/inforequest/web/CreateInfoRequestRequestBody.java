package com.wimdeblauwe.examples.errorhandling.inforequest.web;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CreateInfoRequestRequestBody {
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
    @Email
    @NotBlank
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
