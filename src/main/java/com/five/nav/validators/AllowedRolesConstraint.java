package com.five.nav.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
@Documented
@Constraint(validatedBy = AllowedRolesValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedRolesConstraint {
  String message() default "Provided value is not allowed for a role!";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
