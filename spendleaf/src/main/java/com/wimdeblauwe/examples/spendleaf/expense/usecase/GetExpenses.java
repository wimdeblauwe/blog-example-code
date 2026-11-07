package com.wimdeblauwe.examples.spendleaf.expense.usecase;

import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.repository.ExpenseRepository;
import com.wimdeblauwe.examples.spendleaf.infrastructure.stereotype.UseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase(readOnly = true)
public class GetExpenses {
    private final ExpenseRepository repository;

    public GetExpenses(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Page<Expense> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
