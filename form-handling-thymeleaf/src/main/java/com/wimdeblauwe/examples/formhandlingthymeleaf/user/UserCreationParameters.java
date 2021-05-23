package com.wimdeblauwe.examples.formhandlingthymeleaf.user;

import org.springframework.util.Assert;

public class UserCreationParameters {
    private final String givenName;
    private final String familyName;

    public UserCreationParameters(String givenName,
                                  String familyName) {
        Assert.notNull(givenName, "givenName should not be null");
        Assert.notNull(familyName, "familyName should not be null");
        this.givenName = givenName;
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }
}
