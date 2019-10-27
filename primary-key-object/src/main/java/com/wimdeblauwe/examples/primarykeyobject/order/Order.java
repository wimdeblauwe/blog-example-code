package com.wimdeblauwe.examples.primarykeyobject.order;

import com.wimdeblauwe.examples.primarykeyobject.infrastructure.jpa.Entity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "`order`")
public class Order implements Entity<OrderId> {
    @Id
    @GenericGenerator(
            name = "assigned-sequence",
            strategy = "com.wimdeblauwe.examples.primarykeyobject.order.OrderIdIdentifierGenerator")
    @GeneratedValue(
            generator = "assigned-sequence",
            strategy = GenerationType.SEQUENCE)
    private OrderId id;

    private String name;

    public Order(String name) {
        this.name = name;
    }

    public OrderId getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
