package com.wimdeblauwe.examples.spendleaf.expense.usecase;

import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseId;
import com.wimdeblauwe.examples.spendleaf.expense.repository.ExpenseRepository;
import com.wimdeblauwe.examples.spendleaf.infrastructure.stereotype.UseCase;

@UseCase(readOnly = true)
public class GetExpense {
    private final ExpenseRepository repository;

    public GetExpense(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Expense execute(ExpenseId id) {
        return repository.getById(id);
    }
}
