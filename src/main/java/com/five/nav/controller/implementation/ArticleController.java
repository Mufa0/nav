package com.five.nav.controller.implementation;

import com.five.nav.controller.ArticleControllerInterface;
import com.five.nav.domain.User;
import com.five.nav.exception.UserNotAllowedForThisActionException;
import com.five.nav.exception.UserNotAuthenticatedException;
import com.five.nav.repository.UserRepository;
import com.five.nav.request.ArticleRequest;
import com.five.nav.response.ArticleResponse;
import com.five.nav.service.ArticleServiceInterface;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class ArticleController implements ArticleControllerInterface {

  ArticleServiceInterface articleService;

  UserRepository userRepository;

  @Override
  public ArticleResponse createArticle(ArticleRequest articleRequest, Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("Creating article with title: %s",articleRequest.getTitle()));
      return articleService.createArticle(articleRequest, user.get());
    }else{
      log.error("User not authenticated but accessed protected URL!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }

  @Override
  public List<ArticleResponse> getArticles() {
    log.info("Finding all articles in database ( no sorting or paging )");
    return articleService.getAllArticles();
  }

  @Override
  public List<ArticleResponse> getArticlesForUser(long userId, Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()) {
      if(user.get().getId() != userId){
        log.error(String.format("User %s trying to access articles from %d",principal.getName(),
            userId));
        throw new UserNotAllowedForThisActionException(principal.getName());
      }else{
        return articleService.getAllArticlesForAuthor(user.get());
      }
    }else{
      log.error("User not authenticated but accessed protected URL!");
      throw new UserNotAllowedForThisActionException(principal.getName());
    }
  }

  @Override
  public ArticleResponse getArticle(long id) {
    log.info(String.format("Finding article with id: %d !",id));
    return articleService.getArticle(id);
  }

  @Override
  public ArticleResponse updateArticle(long id, ArticleRequest articleRequest,
      Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("Updating article with id: %d",id));
      return articleService.updateArticle(id, articleRequest, user.get());
    }else{
      log.error("User not authenticated but accessed protected URL!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }

  @Override
  public void deleteArticle(long id, Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("Deleting article with id: %d",id));
      articleService.delete(id, user.get());
    }else{
      log.error("User not authenticated but accessed protected URL!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }
}
