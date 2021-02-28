package com.five.nav.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {

  long id;

  String title;

  String content;

  long author;

  int numberOfLikes;
}
