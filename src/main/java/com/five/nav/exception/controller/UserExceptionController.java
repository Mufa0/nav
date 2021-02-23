package com.five.nav.exception.controller;

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
  MessageSource messageSource;

  @ExceptionHandler(value = UserNotFoundException.class)
  public ResponseEntity<String> userNotFoundHandler(UserNotFoundException e, Locale locale){
    return new ResponseEntity<>(messageSource.getMessage(NOT_FOUND_MESSAGE, new Object[]{e.getUserId()},locale), HttpStatus.NOT_FOUND);
  }
}
