package com.five.nav.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuditResponse {

  long id;

  String action;

  String timestamp;

  String commiterEmail;

  String message;
}
