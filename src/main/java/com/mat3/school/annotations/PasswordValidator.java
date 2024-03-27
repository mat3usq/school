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

    String message() default "Please choose a stronger password";
}
