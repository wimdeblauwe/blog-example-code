package com.wimdeblauwe.examples.primarykeyobject.order;

import com.wimdeblauwe.examples.primarykeyobject.infrastructure.jpa.AbstractLongEntityIdIdentifierGenerator;

public class OrderIdIdentifierGenerator extends AbstractLongEntityIdIdentifierGenerator<OrderId> {

    @Override
    protected OrderId createEntityId(long seqValue) {
        return new OrderId(seqValue);
    }
}
