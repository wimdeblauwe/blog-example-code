package com.wimdeblauwe.examples.spendleaf.expense.repository;

import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseId;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
class JpaExpenseRepository implements ExpenseRepository {
    private final SpringDataJpaExpenseRepository repository;

    JpaExpenseRepository(SpringDataJpaExpenseRepository repository) {
        this.repository = repository;
    }

    @Override
    public ExpenseId nextId() {
        return new ExpenseId(UUID.randomUUID());
    }

    @Override
    public void save(Expense expense) {
        repository.save(expense);
    }

    @Override
    public Expense getById(ExpenseId id) {
        return repository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));
    }

    @Override
    public Page<Expense> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
