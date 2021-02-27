package com.five.nav.exception;

import lombok.Getter;

@Getter
public class ArticleNotSavedException extends RuntimeException {

  public ArticleNotSavedException(){}

  public ArticleNotSavedException(String message){
    super(message);
  }
}
