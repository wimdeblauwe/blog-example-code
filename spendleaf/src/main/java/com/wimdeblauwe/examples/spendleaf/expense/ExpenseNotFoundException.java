package com.wimdeblauwe.examples.spendleaf.expense;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExpenseNotFoundException extends RuntimeException {

    public ExpenseNotFoundException(ExpenseId id) {
        super("Expense with id %s not found".formatted(id.getId()));
    }
}
