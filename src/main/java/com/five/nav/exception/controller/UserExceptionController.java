package com.five.nav.exception.controller;

import com.five.nav.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {

  @ExceptionHandler(value = UserNotFoundException.class)
  public ResponseEntity<String> userNotFoundHandler(UserNotFoundException e){
    return new ResponseEntity<>(String.format("User with id: {} not found", e.getUserId()), HttpStatus.NOT_FOUND);
  }
}
