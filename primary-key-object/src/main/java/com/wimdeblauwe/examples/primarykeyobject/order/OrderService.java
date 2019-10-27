package com.wimdeblauwe.examples.primarykeyobject.order;

public interface OrderService {
    Order getOrder(Long orderId, Long userId);
}
