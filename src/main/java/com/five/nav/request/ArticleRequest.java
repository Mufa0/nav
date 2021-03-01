package com.five.nav.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest implements Request{

  private static final String TITLE_NOT_VALID="Exception.article.titleNotValid";
  private static final String CONTENT_NOT_VALID="Exception.article.contentNotValid";

  @NotBlank(message=TITLE_NOT_VALID)
  String title;

  @NotBlank(message=CONTENT_NOT_VALID)
  String content;

}
