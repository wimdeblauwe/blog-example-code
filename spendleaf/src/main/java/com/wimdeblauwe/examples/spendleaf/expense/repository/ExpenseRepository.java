package com.wimdeblauwe.examples.spendleaf.expense.repository;

import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseRepository {
    ExpenseId nextId();

    void save(Expense expense);

    Expense getById(ExpenseId id);

    Page<Expense> findAll(Pageable pageable);

    void deleteAll();
}
