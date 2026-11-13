package com.wimdeblauwe.examples.spendleaf.expense.repository;

import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseId;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseNotFoundException;
import com.wimdeblauwe.examples.spendleaf.project.Project;
import com.wimdeblauwe.examples.spendleaf.project.ProjectId;
import com.wimdeblauwe.examples.spendleaf.project.repository.ProjectRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;

/**
 * In-memory implementation for use case tests.
 * Without a sort, it pages in insertion order.
 * Sorting on the project sorts on the project's name, as the JPA implementation does.
 */
public class InMemoryExpenseRepository implements ExpenseRepository {
    private final ProjectRepository projectRepository;
    // Nulls last, so a descending sort puts them first: the same as PostgreSQL does by default
    private final Map<String, Comparator<Expense>> comparators = Map.of(
            "id", comparing(expense -> expense.getId().getId()),
            "description", comparing(Expense::getDescription),
            "amount", comparing(expense -> expense.getAmount().value()),
            "date", comparing(Expense::getDate),
            "category", comparing(Expense::getCategory),
            "project", comparing(this::projectName, nullsLast(naturalOrder())),
            "billable", comparing(Expense::isBillable),
            "notes", comparing(Expense::getNotes, nullsLast(naturalOrder())),
            "status", comparing(Expense::getStatus));

    private final Map<ExpenseId, Expense> values = new LinkedHashMap<>();

    public InMemoryExpenseRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ExpenseId nextId() {
        return new ExpenseId(UUID.randomUUID());
    }

    @Override
    public void save(Expense expense) {
        values.put(expense.getId(), expense);
    }

    @Override
    public Expense getById(ExpenseId id) {
        Expense expense = values.get(id);
        if (expense == null) {
            throw new ExpenseNotFoundException(id);
        }
        return expense;
    }

    @Override
    public Page<Expense> findAll(Pageable pageable) {
        List<Expense> content = values.values().stream()
                .sorted(comparator(pageable.getSort()))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();
        return new PageImpl<>(content, pageable, values.size());
    }

    @Override
    public void deleteAll() {
        values.clear();
    }

    private @Nullable String projectName(Expense expense) {
        ProjectId projectId = expense.getProject();
        return projectRepository.findAll().stream()
                .filter(project -> project.getId().equals(projectId))
                .map(Project::getName)
                .findFirst()
                .orElse(null);
    }

    private Comparator<Expense> comparator(Sort sort) {
        Comparator<Expense> result = (first, second) -> 0;
        for (Sort.Order order : sort) {
            Comparator<Expense> comparator = comparators.get(order.getProperty());
            if (comparator == null) {
                throw new IllegalArgumentException("Unknown sort property for Expense: " + order.getProperty());
            }
            result = result.thenComparing(order.isAscending() ? comparator : comparator.reversed());
        }
        return result;
    }
}
