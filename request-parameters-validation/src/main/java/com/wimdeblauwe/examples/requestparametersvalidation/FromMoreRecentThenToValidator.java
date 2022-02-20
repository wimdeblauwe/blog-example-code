package com.wimdeblauwe.examples.requestparametersvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FromMoreRecentThenToValidator implements ConstraintValidator<FromMoreRecentThenTo, GetTaskRequestParameters> {
    @Override
    public boolean isValid(GetTaskRequestParameters value,
                           ConstraintValidatorContext context) {
        if (value.from().isAfter(value.to())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("From (%s) is after to (%s), which is invalid.", value.from(), value.to()))
                   .addConstraintViolation();
            return false;
        }
        return true;
    }
}
