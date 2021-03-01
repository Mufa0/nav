package com.five.nav.service;

import com.five.nav.domain.Article;
import com.five.nav.domain.User;
import com.five.nav.enums.Action;
import com.five.nav.response.ArticleAuditResponse;
import com.five.nav.response.GroupedArticleAuditResponse;
import java.util.List;

public interface ArticleAuditServiceInterface {

  boolean createAudit(User user, Article article, String message, Action action);

  List<GroupedArticleAuditResponse> findArticleAuditsForUser(User user);

  List<ArticleAuditResponse> findArticleAuditsForArticle(Article article, User user);

  ArticleAuditResponse findArticleAudit(long id, User user);
}
