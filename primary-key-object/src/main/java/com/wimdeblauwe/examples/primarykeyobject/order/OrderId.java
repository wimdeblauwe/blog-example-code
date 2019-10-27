package com.wimdeblauwe.examples.primarykeyobject.order;

import com.wimdeblauwe.examples.primarykeyobject.infrastructure.jpa.AbstractLongEntityId;

public class OrderId extends AbstractLongEntityId {
    public OrderId(Long value) {
        super(value);
    }
}
