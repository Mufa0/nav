package com.five.nav.service.implementation;

import com.five.nav.domain.Article;
import com.five.nav.domain.User;
import com.five.nav.enums.Action;
import com.five.nav.enums.ArticleStatus;
import com.five.nav.exception.ArticleNotFoundException;
import com.five.nav.exception.ArticleNotSavedException;
import com.five.nav.exception.UserNotAllowedForThisActionException;
import com.five.nav.mapper.ArticleMapper;
import com.five.nav.repository.ArticleRepository;
import com.five.nav.request.ArticleRequest;
import com.five.nav.response.ArticleResponse;
import com.five.nav.service.ArticleAuditServiceInterface;
import com.five.nav.service.ArticleServiceInterface;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService implements ArticleServiceInterface {

  ArticleRepository articleRepository;
  ArticleMapper mapper;
  ArticleAuditServiceInterface articleAuditService;


  @Override
  public ArticleResponse createArticle(ArticleRequest articleRequest, User user) {

    Article article = mapper.from(articleRequest);
    article.setAuthor(user);

    try{
      article = articleRepository.save(article);

      String message = String.format("Article with id: %d and title: %s saved to database!",
          article.getId()
          , article.getTitle());

      log.info(message);

      articleAuditService.createAudit(user, article, message, Action.CREATE);

      return mapper.from(article);
    }catch (Exception e){
      log.error(String.format("Article with title: %s could not be saved. Message: %s.",
          articleRequest.getTitle(), e));
      throw new ArticleNotSavedException(e.getMessage());
    }
  }

  @Override
  public ArticleResponse updateArticle(long id, ArticleRequest articleRequest, User user) {
    Optional<Article> article = articleRepository.findById(id);
    StringBuilder message = new StringBuilder();
    if(article.isPresent()){
      if(article.get().getAuthor().getId() != user.getId()){
        log.error(String.format("User with id: %d, email: %s trying to delete article with id: %d to "
            + "which "
            + "he/she is not author", user.getId(),user.getEmail(), article.get().getId()));
        throw new UserNotAllowedForThisActionException(user.getEmail());
      }
      if(!article.get().getTitle().equals(articleRequest.getTitle())){
        message.append(String.format("Article title changed from: %s to %s",
            article.get().getTitle(), articleRequest.getTitle()));
        message.append("\n");
        article.get().setTitle(articleRequest.getTitle());
        log.debug("Article title changed!");
      }
      if(!article.get().getContent().equals(articleRequest.getContent())){
        message.append("Article content changed, no diff displayed for now!");
        log.debug("Article content changed!");
        article.get().setContent(articleRequest.getContent());
      }

      Article updatedArticle = articleRepository.save(article.get());
      log.info(String.format("Article with id: %d  updated !", updatedArticle.getId()));

      articleAuditService.createAudit(user,updatedArticle,message.toString(),Action.UPDATE);

      return mapper.from(updatedArticle);

    }else{
      log.error(String.format("Article with id: %d not found!",id));
      throw new ArticleNotFoundException(id);
    }
  }

  @Override
  public List<ArticleResponse> getAllArticles() {
    List<Article> articles = articleRepository.findAll();
    log.info(String.format("Found %d articles in database!", articles.size()));
    return articles.stream().map(article -> mapper.from(article)).collect(Collectors.toList());
  }

  @Override
  public List<ArticleResponse> getAllArticlesForAuthor(User user){
    List<Article> articles = articleRepository.findAllByAuthor(user.getId());
    log.info(String.format("Found %d articles for user: %s!", articles.size(), user.getEmail()));
    return articles.stream().map(article -> mapper.from(article)).collect(Collectors.toList());
  }

  @Override
  public ArticleResponse getArticle(long id) {
    Optional<Article> article = articleRepository.findById(id);
    if(article.isPresent()){
      log.info(String.format("Finding with id: %d found.", id));
      return mapper.from(article.get());
    }else{
      log.error(String.format("Article with id: %d is not existing",id));
      throw new ArticleNotFoundException(id);
    }

  }

  @Override
  public void delete(long id, User user) {
    Optional<Article> article = articleRepository.findById(id);
    if (article.isPresent()) {
      if (article.get().getAuthor().getId() != user.getId()) {
        log.error(
            "User with id: %d, email: %s trying to delete article with id: %d to which he/she is "
                + "not author");
        throw new UserNotAllowedForThisActionException(user.getEmail());
      }
      article.get().setStatus(ArticleStatus.DELETED);
      articleRepository.save(article.get());

      String message = String.format("Article with id: %d succefully deleted!", id);
      articleAuditService.createAudit(user, article.get(), message, Action.DELETE);
      log.info(message);

    }
  }
}
