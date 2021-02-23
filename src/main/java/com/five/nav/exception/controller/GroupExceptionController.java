package com.five.nav.exception.controller;

import com.five.nav.exception.GroupNotFoundException;
import java.util.Locale;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
public class GroupExceptionController {

  private static final String NOT_FOUND_MESSAGE = "Exception.group.notFound";
  MessageSource messageSource;

  @ExceptionHandler(value = GroupNotFoundException.class)
  public ResponseEntity<String> groupNotFoundHandler(GroupNotFoundException e, Locale locale){
    return new ResponseEntity<>(messageSource.getMessage(NOT_FOUND_MESSAGE,new Object[]{e.getGroupId()},locale), HttpStatus.NOT_FOUND);
  }
}
