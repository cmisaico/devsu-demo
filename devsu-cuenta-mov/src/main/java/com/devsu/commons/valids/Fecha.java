package com.devsu.commons.valids;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "El formato de la fecha debe ser dd/MM/YYYY")
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
public @interface Fecha {

    String message() default "El formato de la fecha debe ser dd/MM/YYYY";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}