package com.five.nav.exception;

import lombok.Getter;

@Getter
public class ArticleNotFoundException extends RuntimeException {

  final long articleId;

  public ArticleNotFoundException(long articleId){
    super();
    this.articleId = articleId;
  }

  public ArticleNotFoundException(long articleId, String message){
    super(message);
    this.articleId = articleId;
  }
}
