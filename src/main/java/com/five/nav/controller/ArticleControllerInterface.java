package com.five.nav.controller;

import com.five.nav.request.ArticleRequest;
import com.five.nav.response.ArticleResponse;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/articles")
public interface ArticleControllerInterface {

  @PostMapping
  ArticleResponse createArticle(@Valid @RequestBody ArticleRequest articleRequest,
      Principal principal);

  @GetMapping
  List<ArticleResponse> getArticles();

  @GetMapping("/user/{id}")
  List<ArticleResponse> getArticlesForUser(@PathVariable("id") long userId, Principal principal);

  @GetMapping("/{id}")
  ArticleResponse getArticle(@PathVariable("id") long id);



  @PutMapping("/{id}")
  ArticleResponse updateArticle(@PathVariable("id") long id,
      @Valid @RequestBody ArticleRequest articleRequest, Principal principal );

  @DeleteMapping("/{id}")
  void deleteArticle(@PathVariable("id") long id, Principal principal);

}
