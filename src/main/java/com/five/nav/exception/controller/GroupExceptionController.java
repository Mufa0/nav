package com.five.nav.exception.controller;

import com.five.nav.exception.GroupNotFoundException;
import com.five.nav.exception.GroupNotSavedException;
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
  private static final String NOT_SAVED_MESSAGE = "Exception.group.notSaved";
  MessageSource messageSource;

  @ExceptionHandler(value = GroupNotFoundException.class)
  public ResponseEntity<String> groupNotFoundHandler(GroupNotFoundException e, Locale locale){
    return new ResponseEntity<>(messageSource.getMessage(NOT_FOUND_MESSAGE,new Object[]{e.getGroupId()},locale), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = GroupNotSavedException.class)
  public ResponseEntity<String> groupNotSavedHandler(GroupNotSavedException e, Locale locale){
    return new ResponseEntity<>(messageSource.getMessage(NOT_SAVED_MESSAGE,null,locale),
        HttpStatus.BAD_REQUEST);
  }
}
