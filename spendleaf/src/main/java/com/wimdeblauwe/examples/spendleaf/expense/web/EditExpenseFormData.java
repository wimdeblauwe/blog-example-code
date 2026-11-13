package com.wimdeblauwe.examples.spendleaf.expense.web;

import com.wimdeblauwe.examples.spendleaf.expense.Amount;
import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.usecase.EditExpenseParameters;
import com.wimdeblauwe.examples.spendleaf.project.ProjectId;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public class EditExpenseFormData extends AbstractExpenseFormData {
    private @Nullable UUID id;

    public static EditExpenseFormData fromExpense(Expense expense) {
        EditExpenseFormData formData = new EditExpenseFormData();
        formData.setId(expense.getId().getId());
        formData.setDescription(expense.getDescription());
        formData.setAmount(expense.getAmount().value());
        formData.setDate(expense.getDate());
        formData.setCategory(expense.getCategory());
        formData.setProject(expense.getProject() != null ? expense.getProject().getId() : null);
        formData.setBillable(expense.isBillable());
        formData.setNotes(expense.getNotes());
        return formData;
    }

    public EditExpenseParameters toParameters() {
        return new EditExpenseParameters(getDescription(),
                                         new Amount(getAmount()),
                                         getDate(),
                                         getCategory(),
                                         getProject() != null ? new ProjectId(getProject()) : null,
                                         isBillable(),
                                         getNotes());
    }

    public @Nullable UUID getId() {
        return id;
    }

    public void setId(@Nullable UUID id) {
        this.id = id;
    }
}
