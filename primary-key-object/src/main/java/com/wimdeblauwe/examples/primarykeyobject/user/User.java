package com.wimdeblauwe.examples.primarykeyobject.user;

import com.wimdeblauwe.examples.primarykeyobject.infrastructure.jpa.Entity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.EmbeddedId;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@javax.persistence.Entity
public class User implements Entity<UserId> {

    @EmbeddedId
    @GenericGenerator(
            name = "assigned-sequence",
            strategy = "com.wimdeblauwe.examples.primarykeyobject.user.UserIdIdentifierGenerator",
            parameters = {@Parameter(name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY,
                    value = "true"),
                    @Parameter(name = SequenceStyleGenerator.CONFIG_SEQUENCE_PER_ENTITY_SUFFIX,
                            value = "_SEQUENCE")})
    @GeneratedValue(
            generator = "assigned-sequence",
            strategy = GenerationType.SEQUENCE)
    private UserId id;

    private String name;

    public User(String name) {
        this.name = name;
    }

    public UserId getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
