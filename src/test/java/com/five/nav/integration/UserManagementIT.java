package com.five.nav.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.five.nav.NewsArticleViewerApplication;
import com.five.nav.Query;
import com.five.nav.enums.Role;
import com.five.nav.request.ArticleRequest;
import com.five.nav.request.UserRequest;
import com.five.nav.response.ArticleResponse;
import com.five.nav.response.UserResponse;
import com.five.nav.utils.IntegrationTestsUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NewsArticleViewerApplication.class, webEnvironment =
    WebEnvironment.DEFINED_PORT)
@ActiveProfiles({"default","test"})
@TestInstance(Lifecycle.PER_CLASS)
public class UserManagementIT  {

  ObjectMapper objectMapper = new ObjectMapper();
  IntegrationTestsUtils utils = new IntegrationTestsUtils(objectMapper);

  String authorEmail = "author@test.com";
  String secondAuthorEmail = "secondAuthor@test.com";
  String readerEmail = "reader@test.com";

  String pass = "pass";

  String registerPath = "/users/register";
  String usersPath = "/users";
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
  void registerUser_ExpectPositiveResult() throws JsonProcessingException {
    UserRequest request = UserRequest.builder().name("Test").lastname("Test").email("test@test"
        + ".com").password("pass").build();

    ResponseEntity<String> response =
        Query.builder().path("/users/register").httpMethod(HttpMethod.POST).request(request).build().invoke();

    UserResponse userResponse = objectMapper.readValue(response.getBody(),UserResponse.class);
    assertThat(userResponse).isNotNull();

  }

  @Test
  void likeArticle_checkOnUserSide_ExpectPositiveResult() throws JsonProcessingException{

    ArticleRequest request = ArticleRequest.builder().title("Should pass").content("Should pass").build();
    ArticleResponse article = utils.create(request, articlesPath,authorEmail, pass, ArticleResponse.class);

    utils.update(null, usersPath+"/article/"+article.getId(), readerEmail, pass);

    UserResponse user = utils.get(usersPath+"/user", readerEmail, pass, UserResponse.class);

    assertThat(user.getLikedArticles()).contains(article.getId());

  }

  @Test
  void likeArticle_checkOnArticleSide_ExpectPositiveResult() throws JsonProcessingException{

    ArticleRequest request = ArticleRequest.builder().title("Should pass").content("Should pass").build();
    ArticleResponse article = utils.create(request, articlesPath,authorEmail, pass, ArticleResponse.class);

    utils.update(null, usersPath+"/article/"+article.getId(), readerEmail, pass);
    UserResponse user = utils.get(usersPath+"/user", readerEmail, pass, UserResponse.class);

    ArticleResponse afterLike = utils.get(articlesPath+"/"+article.getId(), readerEmail, pass,
        ArticleResponse.class);

    assertThat(article.getNumberOfLikes()).isEqualTo(afterLike.getNumberOfLikes()-1);

  }



}
