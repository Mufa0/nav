package com.five.nav.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

  final long userId;

  public UserNotFoundException(long userId){
    super();
    this.userId = userId;
  }

  public UserNotFoundException(long userId, String message){
    super(message);
    this.userId = userId;
  }
}
