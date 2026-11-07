package com.wimdeblauwe.examples.spendleaf.expense;

import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.UUID;

public class ExpenseMother {

    public static Builder expense() {
        return new Builder();
    }

    public static class Builder {
        private ExpenseId id = new ExpenseId(UUID.randomUUID());
        private String description = "Hotel Ghent, 2 nights";
        private Amount amount = Amount.ofEuros("238.00");
        private LocalDate date = LocalDate.of(2026, 11, 10);
        private ExpenseCategory category = ExpenseCategory.TRAVEL;
        private @Nullable String project = "Atlas";
        private boolean billable = true;
        private @Nullable String notes = "Two nights for the workshop at the client's office in Ghent.";

        public Builder id(ExpenseId id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder amount(Amount amount) {
            this.amount = amount;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder category(ExpenseCategory category) {
            this.category = category;
            return this;
        }

        public Builder project(@Nullable String project) {
            this.project = project;
            return this;
        }

        public Builder billable(boolean billable) {
            this.billable = billable;
            return this;
        }

        public Builder notes(@Nullable String notes) {
            this.notes = notes;
            return this;
        }

        public Expense build() {
            return new Expense(id, description, amount, date, category, project, billable, notes);
        }
    }
}
