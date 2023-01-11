package com.hospital.validators;

import com.hospital.dto.UserRequest;
import com.hospital.validations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserRequest ur = (UserRequest) obj;
        return ur.getPassword().equals(ur.getMatchingPassword());
    }
}
