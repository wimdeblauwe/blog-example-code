package com.wimdeblauwe.examples.spendleaf.expense.web;

import com.wimdeblauwe.examples.spendleaf.expense.Amount;
import com.wimdeblauwe.examples.spendleaf.expense.usecase.CreateExpenseParameters;
import com.wimdeblauwe.examples.spendleaf.project.ProjectId;

import java.time.LocalDate;

public class CreateExpenseFormData extends AbstractExpenseFormData {

    public static CreateExpenseFormData withDefaults() {
        CreateExpenseFormData formData = new CreateExpenseFormData();
        formData.setDate(LocalDate.now());
        return formData;
    }

    public CreateExpenseParameters toParameters() {
        return new CreateExpenseParameters(getDescription(),
                                           new Amount(getAmount()),
                                           getDate(),
                                           getCategory(),
                                           getProject() != null ? new ProjectId(getProject()) : null,
                                           isBillable(),
                                           getNotes());
    }
}
