package com.five.nav.exception;

import lombok.Getter;

@Getter
public class UserNotAuthenticatedException extends RuntimeException {

  final String email;

  public UserNotAuthenticatedException(String email){
    super();
    this.email = email;
  }

  public UserNotAuthenticatedException(String email, String message){
    super(message);
    this.email = email;
  }
}
