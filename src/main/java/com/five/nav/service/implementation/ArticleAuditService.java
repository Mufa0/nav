package com.five.nav.service.implementation;

import static java.util.stream.Collectors.groupingBy;

import com.five.nav.audit.ArticleAudit;
import com.five.nav.domain.Article;
import com.five.nav.domain.User;
import com.five.nav.enums.Action;
import com.five.nav.mapper.ArticleAuditMapper;
import com.five.nav.repository.ArticleAuditRepository;
import com.five.nav.response.ArticleAuditResponse;
import com.five.nav.response.GroupedArticleAuditResponse;
import com.five.nav.service.ArticleAuditServiceInterface;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleAuditService implements ArticleAuditServiceInterface {

  ArticleAuditRepository articleAuditRepository;
  ArticleAuditMapper mapper;

  @Override
  public boolean createAudit(User user, Article article, String message,
      Action action) {
    ArticleAudit audit =
        ArticleAudit.builder().action(action).article(article).timestamp(Calendar.getInstance().getTime()).user(user).message(message).build();

    try{
      articleAuditRepository.save(audit);
      log.info(String.format("Audit for article: %d with action: %s and message: %s by user: %s "
          + "created!",article.getId(), action, message,user.getEmail()));
      return true;
    }catch (Exception e){
      log.error(String.format("Audit for article: %d with action: %s and message: %s by user: %s "
          + "not created!",article.getId(), action, message,user.getEmail()));
      return false;
    }
  }

  @Override
  public List<GroupedArticleAuditResponse> findArticleAuditsForUser(User user) {

    List<ArticleAudit> audits = articleAuditRepository.findAllForUser(user.getId());
    Map<Long, List<ArticleAuditResponse>> mappedAuditResponses =
        audits.stream().map(audit -> mapper.from(audit)).collect(groupingBy(ArticleAuditResponse::getId));

    List<GroupedArticleAuditResponse> groupedAuditResponses =
        mappedAuditResponses.keySet().stream().map( key -> GroupedArticleAuditResponse.builder().articleId(key).audits(mappedAuditResponses.get(key)).build()).collect(
            Collectors.toList());

    log.info(String.format("Fetched %d audits for user articles!", audits.size()));
    return groupedAuditResponses;

  }

  @Override
  public List<ArticleAuditResponse> findArticleAuditsForArticle(Article article, User user) {
    List<ArticleAudit> audits = articleAuditRepository.findAllForArticle(article.getId(),
        user.getId());

    log.info(String.format("Fetched %d audits for article %d!", audits.size(), article.getId()));

    return audits.stream().map(audit -> mapper.from(audit)).collect(Collectors.toList());

  }

  @Override
  public ArticleAuditResponse findArticleAudit(long id, User user) {
    ArticleAudit audit = articleAuditRepository.findById(id, user.getId());
    log.info(String.format("Fetched audit %d for user %d!", id, user.getId()));
    return mapper.from(audit);
  }
}
