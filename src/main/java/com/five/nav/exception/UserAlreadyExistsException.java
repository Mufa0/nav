package com.five.nav.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

  final String userEmail;

  public UserAlreadyExistsException(String userEmail){
    super();
    this.userEmail = userEmail;
  }

  public UserAlreadyExistsException(String userEmail, String message){
    super(message);
    this.userEmail = userEmail;
  }
}
