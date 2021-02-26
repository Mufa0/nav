package com.five.nav.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.five.nav.NewsArticleViewerApplication;
import com.five.nav.Query;
import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class UserManagementIT  {

  ObjectMapper mapper;

  @BeforeEach
  void init(){
    mapper = new ObjectMapper();
  }

  @Test
  void registerUserExpectPositiveResult() throws JsonProcessingException {
    UserRequest request = UserRequest.builder().name("Test").lastname("Test").email("test@test"
        + ".com").password("pass").build();

    ResponseEntity<String> response =
        Query.builder().path("/users/register").httpMethod(HttpMethod.POST).request(request).build().invoke();

    UserResponse userResponse = mapper.readValue(response.getBody(),UserResponse.class);

    assertThat(userResponse).isNotNull();
  }

  @AfterEach
  void clean(){}



}
