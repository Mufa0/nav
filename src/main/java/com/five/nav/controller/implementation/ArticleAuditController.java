package com.five.nav.controller.implementation;

import com.five.nav.controller.ArticleAuditControllerInterface;
import com.five.nav.domain.Article;
import com.five.nav.domain.User;
import com.five.nav.exception.ArticleNotFoundException;
import com.five.nav.exception.UserNotAuthenticatedException;
import com.five.nav.repository.ArticleRepository;
import com.five.nav.repository.UserRepository;
import com.five.nav.response.ArticleAuditResponse;
import com.five.nav.response.GroupedArticleAuditResponse;
import com.five.nav.service.ArticleAuditServiceInterface;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class ArticleAuditController implements ArticleAuditControllerInterface {

  ArticleAuditServiceInterface articleAuditService;

  UserRepository userRepository;

  ArticleRepository articleRepository;

  @Override
  public List<GroupedArticleAuditResponse> getAllArticleAuditsForAuthor(Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("Finding all audits for users: %s audits grouped by audit id!",
          user.get().getEmail()));
      return articleAuditService.findArticleAuditsForUser(user.get());
    }else{
      log.error("User not authenticated but accessed protected URL!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }

  @Override
  public ArticleAuditResponse getArticleAudit(long id, Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if (user.isPresent()) {
      log.info(String.format("Finding audit with id: %d !",
          id));
      return articleAuditService.findArticleAudit(id, user.get());
    }else{
      log.error("User not authenticated but accessed protected URL!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }

  @Override
  public List<ArticleAuditResponse> getArticleAuditsForArticle(long articleId,
      Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    Optional<Article> article = articleRepository.findById(articleId);
    if(user.isPresent()){
      if(article.isPresent()){
        if(article.get().getAuthor().getId() != user.get().getId()){
          log.error(String.format("User %s trying to access audits from user with id: %d!",
              user.get().getEmail(),article.get().getAuthor().getId()));

          throw new UserNotAuthenticatedException(principal.getName());
        }else{
          log.info(String.format("Finding all audits for article: %d !",
              articleId));

          return articleAuditService.findArticleAuditsForArticle(article.get(),user.get());
        }
      }else{
        log.error(String.format("Article with id: %d not found! ", articleId));
        throw new ArticleNotFoundException(articleId);
      }


    }else{
      log.error("User not authenticated but accessed protected URL!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }
}
