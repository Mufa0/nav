package com.five.nav.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.five.nav.Query;
import com.five.nav.request.Request;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
public class IntegrationTestsUtils {

  ObjectMapper mapper;


  public <T> T create(Request request, String path,
      String email,
      String pass, Class<T> t) throws JsonProcessingException {

    ResponseEntity<String> response =
        Query.builder().path(path).httpMethod(HttpMethod.POST).request(request).build().invoke(email,
            pass);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    T result =  mapper.readValue(response.getBody(), t);

    return result;

  }

  public <T> T getAll(String path, String email, String pass, TypeReference<T> t)
      throws JsonProcessingException {
    ResponseEntity<String>response =
        Query.builder().httpMethod(HttpMethod.GET).path(path).build().invoke(email,pass);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    T result = mapper.readValue(response.getBody(), t);
    return result;
  }

  public <T> T get(String path, String email, String pass, Class<T> t)
      throws JsonProcessingException {
    ResponseEntity<String> response =
        Query.builder().httpMethod(HttpMethod.GET).path(path).build().invoke(email,pass);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    T result = mapper.readValue(response.getBody(), t);
    return result;
  }

  public void delete(String path, String email, String pass){
    Query.builder().path(path).httpMethod(HttpMethod.DELETE).build().invoke(email, pass);
  }

  public void update(Request request, String path, String email, String pass){
    ResponseEntity<String> updateResponse =
        Query.builder().path(path).httpMethod(HttpMethod.PUT).request(request).build().invoke(email,
            pass);
  }
}
