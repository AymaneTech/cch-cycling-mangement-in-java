package com.wora.common.application.validation;

import com.wora.common.application.validation.validator.AdultValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultValidator.class)
public @interface Adult {
    String message() default "Rider must at least have 18, nta jayb lina 9assir";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
