package com.wimdeblauwe.examples.spendleaf.expense.web;

import com.wimdeblauwe.examples.spendleaf.expense.Amount;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseCategory;
import com.wimdeblauwe.examples.spendleaf.expense.usecase.CreateExpenseParameters;
import com.wimdeblauwe.examples.spendleaf.project.ProjectId;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.jspecify.annotations.Nullable;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CreateExpenseFormData {
    @NotBlank
    @Size(max = 200)
    private @Nullable String description;
    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2)
    private @Nullable BigDecimal amount;
    @NotNull
    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private @Nullable LocalDate date;
    @NotNull
    private @Nullable ExpenseCategory category;
    private @Nullable UUID project;
    private boolean billable;
    @Size(max = 1000)
    private @Nullable String notes;

    public static CreateExpenseFormData withDefaults() {
        CreateExpenseFormData formData = new CreateExpenseFormData();
        formData.setDate(LocalDate.now());
        return formData;
    }

    public CreateExpenseParameters toParameters() {
        return new CreateExpenseParameters(description,
                                           new Amount(amount),
                                           date,
                                           category,
                                           project != null ? new ProjectId(project) : null,
                                           billable,
                                           notes);
    }

    public @Nullable String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public @Nullable BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@Nullable BigDecimal amount) {
        this.amount = amount;
    }

    public @Nullable LocalDate getDate() {
        return date;
    }

    public void setDate(@Nullable LocalDate date) {
        this.date = date;
    }

    public @Nullable ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(@Nullable ExpenseCategory category) {
        this.category = category;
    }

    public @Nullable UUID getProject() {
        return project;
    }

    public void setProject(@Nullable UUID project) {
        this.project = project;
    }

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    public @Nullable String getNotes() {
        return notes;
    }

    public void setNotes(@Nullable String notes) {
        this.notes = notes;
    }
}
