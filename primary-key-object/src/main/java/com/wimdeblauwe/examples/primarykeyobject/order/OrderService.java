package com.wimdeblauwe.examples.primarykeyobject.order;

import com.wimdeblauwe.examples.primarykeyobject.user.UserId;

public interface OrderService {
    Order getOrder(OrderId orderId, UserId userId);
}
