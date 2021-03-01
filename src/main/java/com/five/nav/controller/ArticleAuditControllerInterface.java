package com.five.nav.controller;

import com.five.nav.response.ArticleAuditResponse;
import com.five.nav.response.GroupedArticleAuditResponse;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/article-audits")
public interface ArticleAuditControllerInterface {

  @GetMapping("/user")
  List<GroupedArticleAuditResponse> getAllArticleAuditsForAuthor(Principal principal);

  @GetMapping("/{id}")
  ArticleAuditResponse getArticleAudit(@PathVariable("id") long id, Principal principal);

  @GetMapping("/article/{id}")
  List<ArticleAuditResponse> getArticleAuditsForArticle(@PathVariable("id") long articleId,
      Principal principal);

}
