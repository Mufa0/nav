package com.five.nav.exception;

import lombok.Getter;

@Getter
public class GroupNotFoundException extends RuntimeException {

  final long groupId;

  public GroupNotFoundException(long groupId){
    super();
    this.groupId = groupId;
  }

  public GroupNotFoundException(long groupId, String message){
    super(message);
    this.groupId = groupId;
  }
}
