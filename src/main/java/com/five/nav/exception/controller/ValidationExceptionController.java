package com.five.nav.exception.controller;

import java.util.Locale;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
public class ValidationExceptionController {
  MessageSource messageSource;

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, Locale locale){
    StringBuilder message = new StringBuilder();
    for(FieldError error : e.getFieldErrors()){
          message.append(messageSource.getMessage(error.getDefaultMessage(),null, locale));
          message.append("\n");
    }

    return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
  }

}
