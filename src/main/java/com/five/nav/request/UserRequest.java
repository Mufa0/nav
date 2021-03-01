package com.five.nav.request;

import com.five.nav.validators.AllowedRolesConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest implements  Request{

  private static final String NAME_NOT_VALID = "Exception.user.nameNotValid";
  private static final String LASTNAME_NOT_VALID = "Exception.user.lastnameNotValid";
  private static final String EMAIL_NOT_VALID = "Exception.user.emailNotValid";
  private static final String PASSWORD_NOT_VALID = "Exception.user.passwordNotValid";
  private static final String ROLE_NOT_VALID = "Exception.user.roleNotValid";
  @NotBlank(message = NAME_NOT_VALID)
  String name;

  @NotBlank(message = LASTNAME_NOT_VALID)
  String lastname;

  @Email(message = EMAIL_NOT_VALID)
  @NotBlank(message = EMAIL_NOT_VALID)
  String email;

  @NotBlank(message = PASSWORD_NOT_VALID)
  String password;

  @AllowedRolesConstraint(message = ROLE_NOT_VALID)
  String role;

}
