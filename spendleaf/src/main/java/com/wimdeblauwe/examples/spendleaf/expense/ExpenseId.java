package com.wimdeblauwe.examples.spendleaf.expense;

import io.github.wimdeblauwe.jpearl.AbstractEntityId;

import java.util.UUID;

public class ExpenseId extends AbstractEntityId<UUID> {

    protected ExpenseId() {
    }

    public ExpenseId(UUID id) {
        super(id);
    }
}
