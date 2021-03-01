package com.wimdeblauwe.examples.attributeconvertervsembeddable.embeddable;

import javax.persistence.Embeddable;

@Embeddable
public class NaturalPersonName {
    private String givenName;
    private String familyName;

    protected NaturalPersonName() {
    }

    public NaturalPersonName(String givenName,
                             String familyName) {
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
