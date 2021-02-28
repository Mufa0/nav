package com.five.nav.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest implements  Request{

  private static final String NAME_NOT_VALID = "Exception.user.nameNotValid";
  private static final String LASTNAME_NOT_VALID = "Exception.user.lastnameNotValid";
  private static final String EMAIL_NOT_VALID = "Exception.user.emailNotValid";
  private static final String PASSWORD_NOT_VALID = "Exception.user.passwordNotValid";

  @NotBlank(message = NAME_NOT_VALID)
  String name;

  @NotBlank(message = LASTNAME_NOT_VALID)
  String lastname;

  @Email(message = EMAIL_NOT_VALID)
  @NotBlank(message = EMAIL_NOT_VALID)
  String email;

  @NotBlank(message = PASSWORD_NOT_VALID)
  String password;

  String role;

}
