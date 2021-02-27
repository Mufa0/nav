package com.five.nav.exception;

import lombok.Getter;

@Getter
public class UserNotAllowedForThisActionException extends RuntimeException {

  final String email;

  public UserNotAllowedForThisActionException(String email){
    super();
    this.email = email;
  }

  public UserNotAllowedForThisActionException(String email, String message){
    super(message);
    this.email = email;
  }
}
