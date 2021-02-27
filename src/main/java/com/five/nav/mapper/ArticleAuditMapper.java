package com.five.nav.mapper;

import com.five.nav.audit.ArticleAudit;
import com.five.nav.response.ArticleAuditResponse;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ArticleAuditMapper {

  public ArticleAuditResponse from(ArticleAudit audit) {
    return ArticleAuditResponse.builder().id(audit.getId()).action(audit.getAction().toString())
        .articleId(audit.getArticle().getId()).commiterEmail(audit.getUser().getEmail())
        .message(audit.getMessage()).timestamp(audit.getTimestamp().toString()).build();
  }
}
