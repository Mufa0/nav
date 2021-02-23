package com.five.nav.exception.controller;

import com.five.nav.exception.ArticleNotFoundException;
import java.util.Locale;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
public class ArticleExceptionController {
  private static final String NOT_FOUND_MESSAGE = "Exception.article.notFound";
  MessageSource messageSource;

  @ExceptionHandler(value = ArticleNotFoundException.class)
  public ResponseEntity<String> articleNotFoundHandler(ArticleNotFoundException e, Locale locale){
    return new ResponseEntity<>(messageSource.getMessage(NOT_FOUND_MESSAGE,new Object[]{e.getArticleId()},locale), HttpStatus.NOT_FOUND);
  }
}
