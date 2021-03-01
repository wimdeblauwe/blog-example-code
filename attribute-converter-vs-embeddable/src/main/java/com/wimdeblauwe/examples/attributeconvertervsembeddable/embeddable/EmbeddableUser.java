package com.wimdeblauwe.examples.attributeconvertervsembeddable.embeddable;

import javax.persistence.*;

@Entity
public class EmbeddableUser {
    @EmbeddedId
    private UserId id;
    private NaturalPersonName name;
    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "value", column = @Column(name = "personal_email")))
    private Email personalEmail;
    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "value", column = @Column(name = "work_email")))
    private Email workEmail;

    protected EmbeddableUser() {
    }

    public EmbeddableUser(UserId id,
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
