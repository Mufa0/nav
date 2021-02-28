package com.five.nav.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.five.nav.NewsArticleViewerApplication;
import com.five.nav.Query;
import com.five.nav.domain.Article;
import com.five.nav.enums.Role;
import com.five.nav.request.ArticleRequest;
import com.five.nav.request.UserRequest;
import com.five.nav.response.ArticleResponse;
import com.five.nav.utils.ArticleManagementITUtils;
import com.five.nav.utils.GroupManagementITUtils;
import javafx.beans.binding.StringExpression;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = NewsArticleViewerApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"default","test"})
@TestInstance(Lifecycle.PER_CLASS)
public class GroupManagementIT {

  ObjectMapper objectMapper = new ObjectMapper();
  ArticleManagementITUtils articleUtils = new ArticleManagementITUtils(objectMapper);
  GroupManagementITUtils groupUtils = new GroupManagementITUtils(objectMapper);

  String authorEmail = "author@test.com";
  String secondAuthorEmail = "secondAuthor@test.com";
  String readerEmail = "reader@test.com";

  String pass = "pass";

  String registerPath = "/users/register";
  String articlesPath = "/articles";

  ArticleResponse articleOne;
  ArticleResponse articleTwo;
  ArticleResponse articleThree;
  ArticleResponse articleFour;

  @BeforeAll
  void init() throws JsonProcessingException {
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


    articleOne = articleUtils.createArticle("Article 1", "Content of article 1", articlesPath,
        authorEmail, pass);
    articleTwo = articleUtils.createArticle("Article 2", "Content of article 2", articlesPath,
        authorEmail, pass);

    articleThree = articleUtils.createArticle("Article 3", "Content of article 3", articlesPath,
        secondAuthorEmail, pass);
    articleFour = articleUtils.createArticle("Article 4", "Content of article 4", articlesPath,
        secondAuthorEmail, pass);
  }


}
