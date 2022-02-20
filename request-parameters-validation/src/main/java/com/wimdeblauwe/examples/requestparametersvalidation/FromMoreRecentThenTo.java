package com.wimdeblauwe.examples.requestparametersvalidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FromMoreRecentThenToValidator.class)
public @interface FromMoreRecentThenTo {
    String message() default "`from` should be more recent then `to`";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
