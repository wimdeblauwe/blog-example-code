package com.wimdeblauwe.examples.spendleaf.expense.usecase;

import com.wimdeblauwe.examples.spendleaf.expense.Amount;
import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.repository.InMemoryExpenseRepository;
import com.wimdeblauwe.examples.spendleaf.project.repository.InMemoryProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static com.wimdeblauwe.examples.spendleaf.expense.ExpenseMother.expense;
import static org.assertj.core.api.Assertions.assertThat;

class GetExpensesTest {

    private InMemoryExpenseRepository repository;
    private GetExpenses getExpenses;

    @BeforeEach
    void setUp() {
        repository = new InMemoryExpenseRepository(new InMemoryProjectRepository());
        getExpenses = new GetExpenses(repository);
    }

    @Test
    void testGetExpenses() {
        repository.save(expense().id(repository.nextId()).description("Taxi").build());
        repository.save(expense().id(repository.nextId()).description("Hotel").build());
        repository.save(expense().id(repository.nextId()).description("Lunch").build());

        Page<Expense> page = getExpenses.execute(PageRequest.of(1, 2));

        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getContent())
                .extracting(Expense::getDescription)
                .containsExactly("Lunch");
    }

    @Test
    void testGetExpenses_sorted() {
        repository.save(expense().id(repository.nextId()).description("Taxi").amount(Amount.ofEuros("42.50")).build());
        repository.save(expense().id(repository.nextId()).description("Hotel").amount(Amount.ofEuros("238.00")).build());
        repository.save(expense().id(repository.nextId()).description("Lunch").amount(Amount.ofEuros("18.00")).build());

        Page<Expense> page = getExpenses.execute(PageRequest.of(0, 2, Sort.by("amount").descending()));

        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getContent())
                .extracting(Expense::getDescription)
                .containsExactly("Hotel", "Taxi");
    }

    @Test
    void testGetExpenses_noExpenses() {
        Page<Expense> page = getExpenses.execute(PageRequest.of(0, 10));

        assertThat(page.isEmpty()).isTrue();
        assertThat(page.getTotalElements()).isZero();
    }
}
