package com.wimdeblauwe.examples.attributeconvertervsembeddable.attributeconverter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class User {
    @EmbeddedId
    private UserId id;
    private NaturalPersonName name;
    private Email personalEmail;
    private Email workEmail;

    protected User() {
    }

    public User(UserId id,
                NaturalPersonName name,
                Email personalEmail,
                Email workEmail) {
        this.id = id;
        this.name = name;
        this.personalEmail = personalEmail;
        this.workEmail = workEmail;
    }

    public UserId getId() {
        return id;
    }

    public NaturalPersonName getName() {
        return name;
    }

    public Email getPersonalEmail() {
        return personalEmail;
    }

    public Email getWorkEmail() {
        return workEmail;
    }
}
