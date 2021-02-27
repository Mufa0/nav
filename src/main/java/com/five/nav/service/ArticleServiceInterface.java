package com.five.nav.service;

import com.five.nav.domain.User;
import com.five.nav.request.ArticleRequest;
import com.five.nav.response.ArticleResponse;
import java.util.List;

public interface ArticleServiceInterface {

  ArticleResponse createArticle(ArticleRequest articleRequest, User user);

  ArticleResponse updateArticle(long id, ArticleRequest articleRequest, User user);

  List<ArticleResponse> getAllArticles();

  List<ArticleResponse> getAllArticlesForAuthor(User user);

  ArticleResponse getArticle(long id);

  void delete(long id, User user);
}
