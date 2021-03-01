package com.five.nav.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.five.nav.NewsArticleViewerApplication;
import com.five.nav.Query;
import com.five.nav.domain.Article;
import com.five.nav.enums.Role;
import com.five.nav.request.ArticleRequest;
import com.five.nav.request.GroupRequest;
import com.five.nav.request.UserRequest;
import com.five.nav.response.ArticleResponse;
import com.five.nav.response.GroupResponse;
import com.five.nav.utils.IntegrationTestsUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
  IntegrationTestsUtils utils = new IntegrationTestsUtils(objectMapper);

  String authorEmail = "author@test.com";
  String secondAuthorEmail = "secondAuthor@test.com";
  String readerEmail = "reader@test.com";

  String pass = "pass";

  String registerPath = "/users/register";
  String articlesPath = "/articles";
  String groupsPath = "/groups";

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

    ArticleRequest articleOneRequest = ArticleRequest.builder().title("Article 1").content(
        "Content of article 1").build();

    ArticleRequest articleTwoRequest = ArticleRequest.builder().title("Article 2").content(
        "Content of article 2").build();

    ArticleRequest articleThreeRequest = ArticleRequest.builder().title("Article 3").content(
        "Content of article 3").build();

    ArticleRequest articleFourRequest = ArticleRequest.builder().title("Article 4").content(
        "Content of article 4").build();

    articleOne = utils.create(articleOneRequest, articlesPath,
        authorEmail, pass, ArticleResponse.class);
    articleTwo = utils.create(articleTwoRequest, articlesPath,
        authorEmail, pass, ArticleResponse.class);

    articleThree = utils.create(articleThreeRequest, articlesPath,
        secondAuthorEmail, pass,ArticleResponse.class);
    articleFour = utils.create(articleFourRequest, articlesPath,
        secondAuthorEmail, pass,ArticleResponse.class);
  }

  @Test
  void createGroupForReader_ExpectPositiveResult() throws JsonProcessingException {

    List<Long> articles = List.of(articleOne.getId(), articleTwo.getId());
    GroupRequest request = GroupRequest.builder().name("One").articles(articles).build();
    GroupResponse response = utils.create(request, groupsPath, readerEmail, pass,
        GroupResponse.class);

    assertThat(response.getArticles()).contains(articleOne).contains(articleTwo);
  }

  @Test
  void updateGroupForReader_ExpectPositiveResult() throws  JsonProcessingException {
    List<Long> articles = List.of(articleOne.getId(), articleTwo.getId());
    GroupRequest request = GroupRequest.builder().name("Two").articles(articles).build();
    GroupResponse response = utils.create(request, groupsPath, readerEmail, pass,
        GroupResponse.class);

    GroupRequest updateRequest = GroupRequest.builder().name("Three").articles(articles).build();
    utils.update(updateRequest, groupsPath+"/"+response.getId(), readerEmail, pass);

    GroupResponse afterUpdate = utils.get(groupsPath+"/"+response.getId(),readerEmail, pass, GroupResponse.class);

    assertThat(response.getName()).isNotEqualTo(afterUpdate.getName());

  }

  @Test
  void removeArticleFromGroup_ExpectPositiveResult() throws JsonProcessingException {
    List<Long> articles = List.of(articleOne.getId(), articleTwo.getId());

    GroupRequest request = GroupRequest.builder().name("Two").articles(articles).build();

    GroupResponse response = utils.create(request, groupsPath, readerEmail, pass,
        GroupResponse.class);

    GroupRequest updateRequest =
        GroupRequest.builder().name("Two").articles(List.of(articleOne.getId())).build();
    utils.update(updateRequest, groupsPath+"/"+response.getId(), readerEmail, pass);

    GroupResponse afterUpdate = utils.get(groupsPath+"/"+response.getId(),readerEmail, pass, GroupResponse.class);

    assertThat(response.getArticles()).isNotEqualTo(afterUpdate.getArticles());
  }

  @Test
  void getOneSpecificGroup_ExpectPositiveResult() throws JsonProcessingException{
    List<Long> articles = List.of(articleOne.getId(), articleTwo.getId());

    GroupRequest request = GroupRequest.builder().name("Two").articles(articles).build();

    GroupResponse response = utils.create(request, groupsPath, readerEmail, pass,
        GroupResponse.class);

    GroupResponse fetched = utils.get(groupsPath+"/"+response.getId(),readerEmail, pass,
        GroupResponse.class);

    assertThat(response).isEqualTo(fetched);
  }

  @Test
  void deleteGroup_ExpectPositiveResult() throws JsonProcessingException{
    List<Long> articles = List.of(articleOne.getId(), articleTwo.getId());

    GroupRequest groupOneRequest = GroupRequest.builder().name("One").articles(articles).build();
    GroupRequest groupTwoRequest = GroupRequest.builder().name("Two").articles(articles).build();


    GroupResponse groupOne = utils.create(groupOneRequest, groupsPath, readerEmail, pass,
        GroupResponse.class);
    GroupResponse groupTwo = utils.create(groupTwoRequest, groupsPath, readerEmail, pass,
        GroupResponse.class);

    List<GroupResponse> groupsBeforeDeletion = utils.getAll(groupsPath+"/user", readerEmail, pass,
        new TypeReference<List<GroupResponse>>() {});

    utils.delete(groupsPath+"/"+groupOne.getId(), readerEmail, pass);

    List<GroupResponse> groupsAfterDeletion = utils.getAll(groupsPath+"/user", readerEmail, pass,
        new TypeReference<List<GroupResponse>>() {});

    assertThat(groupsBeforeDeletion.size()).isEqualTo(groupsAfterDeletion.size()+1);
  }

}
