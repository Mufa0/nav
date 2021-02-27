package com.five.nav.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.five.nav.Query;
import com.five.nav.request.ArticleRequest;
import com.five.nav.response.ArticleResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
public class ArticleManagementITUtils {

  ObjectMapper objectMapper;

  public ArticleResponse createArticle(String title, String content, String path,
      String email,
      String pass) throws JsonProcessingException {
    ArticleRequest request =
        ArticleRequest.builder().title(title).content(content).build();

    ResponseEntity<String> response =
        Query.builder().path(path).httpMethod(HttpMethod.POST).request(request).build().invoke(email,
            pass);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    ArticleResponse article = objectMapper.readValue(response.getBody(), ArticleResponse.class);

    return article;

  }

  public List<ArticleResponse> getArticles(String path, String email, String pass)
      throws JsonProcessingException {
    ResponseEntity<String>response =
        Query.builder().httpMethod(HttpMethod.GET).path(path).build().invoke(email,pass);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    List<ArticleResponse> allArticles = objectMapper.readValue(response.getBody(), new TypeReference<List<ArticleResponse>>() {});
    return allArticles;
  }

  public ArticleResponse getArticle(String path, String email, String pass)
      throws JsonProcessingException {
    ResponseEntity<String>response =
        Query.builder().httpMethod(HttpMethod.GET).path(path).build().invoke(email,pass);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    ArticleResponse article = objectMapper.readValue(response.getBody(), ArticleResponse.class);
    return article;
  }

  public void deleteArticle(String path, String email, String pass){
        Query.builder().path(path).httpMethod(HttpMethod.DELETE).build().invoke(email, pass);

  }

  public void updateArticle(String title, String content, String path, String email, String pass){
    ArticleRequest updateRequest = ArticleRequest.builder().title(title).content(
        content).build();
    ResponseEntity<String> updateResponse =
        Query.builder().path(path).httpMethod(HttpMethod.PUT).request(updateRequest).build().invoke(email, pass);
  }
}
