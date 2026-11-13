package com.wimdeblauwe.examples.spendleaf.expense.usecase;

import com.wimdeblauwe.examples.spendleaf.expense.Amount;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseCategory;
import com.wimdeblauwe.examples.spendleaf.project.ProjectId;
import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.time.LocalDate;

public record CreateExpenseParameters(String description,
                                      Amount amount,
                                      LocalDate date,
                                      ExpenseCategory category,
                                      @Nullable ProjectId project,
                                      boolean billable,
                                      @Nullable String notes) {
    public CreateExpenseParameters {
        Assert.hasText(description, "The description should not be empty");
        Assert.notNull(amount, "The amount should not be null");
        Assert.notNull(date, "The date should not be null");
        Assert.notNull(category, "The category should not be null");
    }
}
