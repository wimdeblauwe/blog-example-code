package com.wimdeblauwe.examples.spendleaf.expense.repository;

import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface SpringDataJpaExpenseRepository extends CrudRepository<Expense, ExpenseId>,
        PagingAndSortingRepository<Expense, ExpenseId> {
}
