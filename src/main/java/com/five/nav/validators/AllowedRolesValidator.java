package com.five.nav.validators;

import com.five.nav.enums.Role;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AllowedRolesValidator implements ConstraintValidator<AllowedRolesConstraint,
    String> {

  public AllowedRolesValidator(){};

  @Override
  public void initialize(AllowedRolesConstraint constraintAnnotation) {
  }
  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return s == null || 
        s.isBlank() || Arrays.stream(Role.values()).map(role -> role.toString()).collect(Collectors.toList()).contains(s);
  }
}
