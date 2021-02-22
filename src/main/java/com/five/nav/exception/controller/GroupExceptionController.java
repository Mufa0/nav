package com.five.nav.exception.controller;

import com.five.nav.exception.GroupNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GroupExceptionController {

  @ExceptionHandler(value = GroupNotFoundException.class)
  public ResponseEntity<String> articleNotFoundHandler(GroupNotFoundException e){
    return new ResponseEntity<>(String.format("Article with id: {} not found", e.getGroupId()), HttpStatus.NOT_FOUND);
  }
}
