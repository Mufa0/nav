package com.five.nav.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAuditResponse {

  long id;

  String action;

  String timestamp;

  String commiterEmail;

  long articleId;

  String message;

}
