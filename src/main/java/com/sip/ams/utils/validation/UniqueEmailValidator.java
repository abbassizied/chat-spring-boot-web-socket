package com.sip.ams.utils.validation;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.sip.ams.services.UserService;  // Assuming there's a service to check for email uniqueness

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Autowired
    private UserService userService;  // Assuming this service checks for existing emails

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        // Initialization code if needed
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return false;  // null values are considered invalid
        }

        // Validate email format first
        if (!validateEmailFormat(email)) {
            return false;
        }

        // Validate uniqueness using the service
        return userService.isEmailUnique(email);
    }

    private boolean validateEmailFormat(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
