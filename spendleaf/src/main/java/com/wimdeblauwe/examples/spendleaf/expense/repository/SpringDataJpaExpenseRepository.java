package com.wimdeblauwe.examples.spendleaf.expense.repository;

import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface SpringDataJpaExpenseRepository extends CrudRepository<Expense, ExpenseId>,
        PagingAndSortingRepository<Expense, ExpenseId> {

    /**
     * Joins the project of each expense, so the result can be sorted on {@code p.name}.
     * There is no association between the two: an expense only knows the id of its project.
     */
    @Query(value = "select e from Expense e left join Project p on p.id = e.project",
            countQuery = "select count(e) from Expense e")
    Page<Expense> findAllWithProject(Pageable pageable);
}
