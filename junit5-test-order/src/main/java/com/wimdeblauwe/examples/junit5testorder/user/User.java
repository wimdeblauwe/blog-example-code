package com.wimdeblauwe.examples.junit5testorder.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private long id;

    private String name;

    protected User() {
    }

    public User(long id,
                String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
