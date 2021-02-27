package com.five.nav.exception.controller;

import com.five.nav.exception.UserAlreadyExistsException;
import com.five.nav.exception.UserNotAllowedForThisActionException;
import com.five.nav.exception.UserNotAuthenticatedException;
import com.five.nav.exception.UserNotFoundException;
import java.util.Locale;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
public class UserExceptionController {

  private static final String NOT_FOUND_MESSAGE = "Exception.user.notFound";
  private static final String ALREADY_EXISTS_MESSAGE = "Exception.user.userExists";
  private static final String NOT_AUTHENTICATED_MESSAGE = "Exception.user.notAuthenticated";
  private static final String NOT_ALLOWED_MESSAGE = "Exception.user.notAllowedAction";

  MessageSource messageSource;

  @ExceptionHandler(value = UserNotFoundException.class)
  public ResponseEntity<String> userNotFoundHandler(UserNotFoundException e, Locale locale) {
    return new ResponseEntity<>(
        messageSource.getMessage(NOT_FOUND_MESSAGE, new Object[]{e.getUserId()}, locale),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = UserAlreadyExistsException.class)
  public ResponseEntity<String> userAlreadyExistsHandler(UserAlreadyExistsException e,
      Locale locale) {
    return new ResponseEntity<>(
        messageSource.getMessage(ALREADY_EXISTS_MESSAGE, new Object[]{e.getUserEmail()}, locale),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = UserNotAuthenticatedException.class)
  public ResponseEntity<String> serNotAuthenticatedHandler(UserNotAuthenticatedException e,
      Locale locale) {
    return new ResponseEntity<>(messageSource.getMessage(NOT_AUTHENTICATED_MESSAGE,
        new Object[]{e.getEmail()}, locale), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = UserNotAllowedForThisActionException.class)
  public ResponseEntity<String> serNotAuthenticatedHandler(UserNotAllowedForThisActionException e,
      Locale locale) {
    return new ResponseEntity<>(messageSource.getMessage(NOT_ALLOWED_MESSAGE,
        new Object[]{e.getUserId()}, locale), HttpStatus.BAD_REQUEST);
  }


}
