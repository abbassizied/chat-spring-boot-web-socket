package com.sip.ams.utils.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})  // Specify where this annotation can be used
@Constraint(validatedBy = UniqueEmailValidator.class)  // Link to the correct validator class
@Documented
public @interface UniqueEmail {
    String message() default "User with this email already exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
