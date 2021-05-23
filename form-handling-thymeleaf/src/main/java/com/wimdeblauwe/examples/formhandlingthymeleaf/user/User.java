package com.wimdeblauwe.examples.formhandlingthymeleaf.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String givenName;
    private String familyName;

    protected User() {
    }

    public User(String givenName,
                String familyName) {
        this.givenName = givenName;
        this.familyName = familyName;
    }

    public Long getId() {
        return id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
