package com.wimdeblauwe.examples.spendleaf.expense.repository;

import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseId;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Page<Expense> page = repository.findAllWithProject(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                                                                          sortProjectByName(pageable.getSort())));
        // Return the page with the sort that was asked for, so the table still sees it sorted on "project"
        return new PageImpl<>(page.getContent(), pageable, page.getTotalElements());
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    /**
     * An expense only stores the id of its project, so sorting on {@code project} sorts on the name of the joined
     * project instead.
     */
    private static Sort sortProjectByName(Sort sort) {
        return Sort.by(sort.stream()
                               .map(order -> order.getProperty().equals("project") ? order.withProperty("p.name") : order)
                               .toList());
    }
}
