package com.wimdeblauwe.examples.spendleaf.expense;

import io.github.wimdeblauwe.jpearl.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;

@Entity
public class Expense extends AbstractEntity<ExpenseId> {
    private String description;
    private Amount amount;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;
    private @Nullable String project;
    private boolean billable;
    private @Nullable String notes;
    @Enumerated(EnumType.STRING)
    private ExpenseStatus status;

    protected Expense() {
    }

    public Expense(ExpenseId id,
                   String description,
                   Amount amount,
                   LocalDate date,
                   ExpenseCategory category,
                   @Nullable String project,
                   boolean billable,
                   @Nullable String notes) {
        super(id);
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.project = project;
        this.billable = billable;
        this.notes = notes;
        this.status = ExpenseStatus.DRAFT;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public @Nullable String getProject() {
        return project;
    }

    public void setProject(@Nullable String project) {
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

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }
}
