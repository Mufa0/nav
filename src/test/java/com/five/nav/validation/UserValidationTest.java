package com.five.nav.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.five.nav.request.UserRequest;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(Lifecycle.PER_CLASS)
class UserValidationTest {

  private Validator validator;

  private static final String NAME_NOT_VALID = "Exception.user.nameNotValid";
  private static final String LASTNAME_NOT_VALID = "Exception.user.lastnameNotValid";
  private static final String EMAIL_NOT_VALID = "Exception.user.emailNotValid";
  private static final String PASSWORD_NOT_VALID = "Exception.user.passwordNotValid";

  @BeforeAll
  public void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  public void userRegistrationRequest_expectPositiveResult() {
    UserRequest userRequest = UserRequest.builder()
        .name("Mateo")
        .lastname("Stjepanovic")
        .email("testemail@gmail.com")
        .password("password")
        .build();

    List<ConstraintViolation<UserRequest>> violations =
        new ArrayList<>(validator.validate(userRequest));

    assertEquals(0, violations.size());
  }

  @Test
  public void userRegistrationRequest_nameIsBlank_expectNegativeResult(){
    UserRequest userRequest = UserRequest.builder()
        .name("")
        .lastname("Stjepanovic")
        .email("testemail@gmail.com")
        .password("password")
        .build();

    List<ConstraintViolation<UserRequest>> violations =
        new ArrayList<>(validator.validate(userRequest));

    assertEquals(1, violations.size());
    assertEquals(NAME_NOT_VALID, violations.get(0).getMessage());
  }

  @Test
  public void userRegistrationRequest_lastnameIsBlank_expectNegativeResult(){
    UserRequest userRequest = UserRequest.builder()
        .name("Mateo")
        .lastname("")
        .email("testemail@gmail.com")
        .password("password")
        .build();

    List<ConstraintViolation<UserRequest>> violations =
        new ArrayList<>(validator.validate(userRequest));

    assertEquals(1, violations.size());
    assertEquals(LASTNAME_NOT_VALID, violations.get(0).getMessage());
  }

  @Test
  public void userRegistrationRequest_emailIsNotValid_expectNegativeResult(){
    UserRequest userRequest = UserRequest.builder()
        .name("Mateo")
        .lastname("Stjepanovic")
        .email("testemail.com")
        .password("password")
        .build();

    List<ConstraintViolation<UserRequest>> violations =
        new ArrayList<>(validator.validate(userRequest));

    assertEquals(1, violations.size());
    assertEquals(EMAIL_NOT_VALID, violations.get(0).getMessage());
  }

  @Test
  public void userRegistrationRequest_passwordIsNotValid_expectNegativeResult(){
    UserRequest userRequest = UserRequest.builder()
        .name("Mateo")
        .lastname("Stjepanovic")
        .email("testemail@gmail.com")
        .password("")
        .build();

    List<ConstraintViolation<UserRequest>> violations =
        new ArrayList<>(validator.validate(userRequest));

    assertEquals(1, violations.size());
    assertEquals(PASSWORD_NOT_VALID, violations.get(0).getMessage());
  }

  @Test
  public void userRegistrationRequest_emptyRequest_expectNegativeResult(){
    UserRequest userRequest = UserRequest.builder().build();

    List<ConstraintViolation<UserRequest>> violations =
        new ArrayList<>(validator.validate(userRequest));

    assertEquals(4, violations.size());
  }



}
