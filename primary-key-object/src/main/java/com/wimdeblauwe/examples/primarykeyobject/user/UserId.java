package com.wimdeblauwe.examples.primarykeyobject.user;

import com.wimdeblauwe.examples.primarykeyobject.infrastructure.jpa.AbstractLongEntityId;

public class UserId extends AbstractLongEntityId {

    public UserId(Long value) {
        super(value);
    }
}
