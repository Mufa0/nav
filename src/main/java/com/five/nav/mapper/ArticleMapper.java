package com.five.nav.mapper;

import com.five.nav.domain.Article;
import com.five.nav.enums.ArticleStatus;
import com.five.nav.request.ArticleRequest;
import com.five.nav.response.ArticleResponse;
import com.five.nav.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ArticleMapper {

  public Article from(ArticleRequest request) {
    return Article.builder().status(ArticleStatus.ACTIVE).title(request.getTitle())
        .content(request.getContent()).build();
  }

  public ArticleResponse from(Article article) {
    return ArticleResponse.builder().author(article.getAuthor().getId())
        .content(article.getContent()).title(article.getTitle()).id(article.getId()).numberOfLikes(article.getUserWhoLiked() != null ? article.getUserWhoLiked().size() : 0).build();
  }
}
