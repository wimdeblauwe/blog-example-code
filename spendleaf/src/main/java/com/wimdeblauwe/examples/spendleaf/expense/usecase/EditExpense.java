package com.wimdeblauwe.examples.spendleaf.expense.usecase;

import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseId;
import com.wimdeblauwe.examples.spendleaf.expense.repository.ExpenseRepository;
import com.wimdeblauwe.examples.spendleaf.infrastructure.stereotype.UseCase;

@UseCase
public class EditExpense {
    private final ExpenseRepository repository;

    public EditExpense(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Expense execute(ExpenseId id, EditExpenseParameters parameters) {
        Expense expense = repository.getById(id);
        expense.setDescription(parameters.description());
        expense.setAmount(parameters.amount());
        expense.setDate(parameters.date());
        expense.setCategory(parameters.category());
        expense.setProject(parameters.project());
        expense.setBillable(parameters.billable());
        expense.setNotes(parameters.notes());
        repository.save(expense);
        return expense;
    }
}
