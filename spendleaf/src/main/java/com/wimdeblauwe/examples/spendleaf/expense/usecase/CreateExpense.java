package com.wimdeblauwe.examples.spendleaf.expense.usecase;

import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.repository.ExpenseRepository;
import com.wimdeblauwe.examples.spendleaf.infrastructure.stereotype.UseCase;

@UseCase
public class CreateExpense {
    private final ExpenseRepository repository;

    public CreateExpense(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Expense execute(CreateExpenseParameters parameters) {
        Expense expense = new Expense(repository.nextId(),
                                      parameters.description(),
                                      parameters.amount(),
                                      parameters.date(),
                                      parameters.category(),
                                      parameters.project(),
                                      parameters.billable(),
                                      parameters.notes());
        repository.save(expense);
        return expense;
    }
}
