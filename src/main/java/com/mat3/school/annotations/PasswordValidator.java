package com.mat3.school.annotations;

import com.mat3.school.validations.PasswordStrengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default
            "To ensure a password is strong, it must meet the following criteria: " +
                    "Contain at least one uppercase letter, one lowercase letter, one digit, one special character.\n";
}
