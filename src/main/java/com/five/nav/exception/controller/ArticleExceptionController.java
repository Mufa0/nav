package com.five.nav.exception.controller;

import com.five.nav.exception.ArticleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ArticleExceptionController {

  @ExceptionHandler(value = ArticleNotFoundException.class)
  public ResponseEntity<String> articleNotFoundHandler(ArticleNotFoundException e){
    return new ResponseEntity<>(String.format("Article with id: {} not found", e.getArticleId()), HttpStatus.NOT_FOUND);
  }
}
