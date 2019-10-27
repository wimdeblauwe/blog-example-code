package com.wimdeblauwe.examples.primarykeyobject.user;

import com.wimdeblauwe.examples.primarykeyobject.infrastructure.jpa.AbstractLongEntityIdIdentifierGenerator;

public class UserIdIdentifierGenerator extends AbstractLongEntityIdIdentifierGenerator<UserId> {

    @Override
    protected UserId createEntityId(long seqValue) {
        return new UserId(seqValue);
    }
}
