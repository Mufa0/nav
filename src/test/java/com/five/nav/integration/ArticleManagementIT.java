package com.five.nav.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.five.nav.utils.ArticleManagementITUtils;
import com.five.nav.NewsArticleViewerApplication;
import com.five.nav.Query;
import com.five.nav.enums.Role;
import com.five.nav.request.ArticleRequest;
import com.five.nav.request.UserRequest;
import com.five.nav.response.ArticleResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = NewsArticleViewerApplication.class, webEnvironment =
    WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"default","test"})
@TestInstance(Lifecycle.PER_CLASS)
public class ArticleManagementIT {

  ObjectMapper objectMapper = new ObjectMapper();
  ArticleManagementITUtils utils = new ArticleManagementITUtils(objectMapper);

  String authorEmail = "author@test.com";
  String secondAuthorEmail = "secondAuthor@test.com";
  String readerEmail = "reader@test.com";

  String pass = "pass";




  String registerPath = "/users/register";
  String articlesPath = "/articles";

  @BeforeAll
  void init(){
    UserRequest readerRequest =
        UserRequest.builder().name("Reader").lastname("Reader").email(readerEmail).password(pass).role(
            Role.READER.toString()).build();
    UserRequest authorRequest =
        UserRequest.builder().name("Author").lastname("Author").email(authorEmail).password(pass).role(Role.AUTHOR.toString()).build();

    UserRequest secondAuthorRequest =
        UserRequest.builder().name("Author").lastname("Author").email(secondAuthorEmail).password(pass).role(Role.AUTHOR.toString()).build();

    Query.builder().path(registerPath).httpMethod(HttpMethod.POST).request(readerRequest).build().invoke();

    Query.builder().path(registerPath).httpMethod(HttpMethod.POST).request(authorRequest).build().invoke();

    Query.builder().path(registerPath).httpMethod(HttpMethod.POST).request(secondAuthorRequest).build().invoke();



  }

  @Test
  void addArticleWithReaderRole_ExpectNegativeResult(){
    ArticleRequest request =
        ArticleRequest.builder().title("Should fail!").content("Should fail").build();

    ResponseEntity<String> response =
        Query.builder().path(articlesPath).httpMethod(HttpMethod.POST).request(request).build().invoke(readerEmail,pass);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void addArticleWithAuthorRole_ExpectPositiveResult(){
    ArticleRequest request =
        ArticleRequest.builder().title("Should pass!").content("Should pass").build();

    ResponseEntity<String> response =
        Query.builder().path(articlesPath).httpMethod(HttpMethod.POST).request(request).build().invoke(authorEmail, pass);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

  }

  @Test
  void editArticleFromAuthor_ExpectPositiveResult() throws JsonProcessingException {

    ArticleResponse beforeUpdate = utils.createArticle("Should pass", "Should "
        + "pass", articlesPath, authorEmail, pass );


    utils.updateArticle("Changed title!","Should pass",
        articlesPath+"/"+beforeUpdate.getId(), authorEmail, pass);

    ArticleResponse afterUpdate =
        utils.getArticle(articlesPath+"/"+beforeUpdate.getId(),authorEmail, pass);
    assertThat(beforeUpdate).isNotEqualTo(afterUpdate);

  }

  @Test
  void editArticleFromAnotherAuthor_ExpectNegativeResult() throws JsonProcessingException {

    ArticleResponse beforeUpdate = utils.createArticle("Should pass", "Should "
        + "pass", articlesPath, authorEmail, pass);


    utils.updateArticle("Changed title", "Should pass",
        articlesPath+"/"+beforeUpdate.getId(), secondAuthorEmail, pass);

    ArticleResponse afterUpdate =
        utils.getArticle(articlesPath+"/"+beforeUpdate.getId(), authorEmail, pass);

    assertThat(beforeUpdate).isEqualTo(afterUpdate);

  }

  @Test
  void deleteArticleFromAuthor_ExpectPositiveResult() throws JsonProcessingException {

    ArticleResponse article = utils.createArticle("Should pass", "Should pass",
        articlesPath,authorEmail, pass);

    List<ArticleResponse> beforeDeletion = utils.getArticles(articlesPath,
        authorEmail,pass);

    utils.deleteArticle(articlesPath+"/"+article.getId(), authorEmail, pass);

    List<ArticleResponse> afterDeletion = utils.getArticles(articlesPath,
        authorEmail,pass);

    assertThat(beforeDeletion.size()).isEqualTo(afterDeletion.size()+1);

  }

  @Test
  void deleteArticleFromDifferentAuthor_ExpectNegativeResult() throws JsonProcessingException {

    ArticleResponse article = utils.createArticle("Should pass", "Should pass",
        articlesPath, authorEmail, pass);

    List<ArticleResponse> beforeDeletion = utils.getArticles(articlesPath,
        secondAuthorEmail, pass);

    utils.deleteArticle(articlesPath+"/"+article.getId(), secondAuthorEmail, pass);

    List<ArticleResponse> afterDeletion = utils.getArticles(articlesPath,
        secondAuthorEmail, pass);


    assertThat(beforeDeletion.size()).isEqualTo(afterDeletion.size());

  }

  @Test
  void getAllArticlesAsReader_ExpectPositiveResult() throws JsonProcessingException {

    ArticleResponse article = utils.createArticle("Should pass", "Should pass",
        articlesPath, authorEmail, pass);

    List<ArticleResponse> allArticles = utils
        .getArticles(articlesPath,readerEmail
        ,pass);

    assertThat(allArticles).contains(article);

  }

  @Test
  void getAllArticlesAsAuthor_ExpectPositiveResult() throws JsonProcessingException {
    ArticleResponse article = utils.createArticle("Should pass", "Should pass",
        articlesPath, authorEmail, pass);

    List<ArticleResponse> allArticles = utils
        .getArticles(articlesPath,authorEmail
        ,pass);

    assertThat(allArticles).contains(article);
  }

  @Test
  void getAllArticlesFromAuthor_ExpectPositiveResult() throws  JsonProcessingException{
    ArticleResponse article1 = utils.createArticle("Should pass", "Should pass",
        articlesPath, authorEmail, pass);

    List<ArticleResponse> allArticles = utils.getArticles(articlesPath+"/user"
            + "/"+article1.getAuthor().getId(),
        authorEmail
        ,pass);

    assertThat(allArticles).contains(article1);
  }

  @Test
  void getAllArticlesFromAnotherAuthor_ExpectNegativeResult() throws JsonProcessingException{
    ArticleResponse article1 = utils.createArticle("Should pass", "Should pass",
        articlesPath, authorEmail, pass);
    ArticleResponse article2 = utils.createArticle("Should pass", "Should pass",
        articlesPath, secondAuthorEmail, pass);
    ResponseEntity<String>response =
        Query.builder().httpMethod(HttpMethod.GET).path(articlesPath+"/user"
            + "/"+article1.getAuthor().getId()).build().invoke(secondAuthorEmail,pass);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

  }

}
