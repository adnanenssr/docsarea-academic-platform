package com.docsarea.utility;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String > {


    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if ( password == null) {
            return false;
        }

        // Regex: at least 8 chars, 1 uppercase, 1 digit, 1 special char
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}

