package com.five.nav;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Builder
public class Query<T> {



  final static String url = "http://localhost:8080";

  @NotNull
  String path;

  @NotNull
  HttpMethod httpMethod;

  Object request;


  public ResponseEntity<String> invoke() {
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<?> requestEntity;
    if(request != null){
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      requestEntity = new HttpEntity<>(request, headers);
    }else{
      requestEntity = null;
    }

    ResponseEntity<String> responseEntity;
    try {
      responseEntity = restTemplate.exchange(url + path, httpMethod, requestEntity, String.class);
    } catch (HttpClientErrorException e) {
      responseEntity =
          ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
    }

    return responseEntity;

  }

  public ResponseEntity<String> invoke(String username, String password) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBasicAuth(username, password);

    HttpEntity<?> requestEntity = new HttpEntity<>(request, headers);

    ResponseEntity<String> responseEntity;

    try {
      responseEntity = restTemplate.exchange(url + path, httpMethod, requestEntity, String.class);
    } catch (HttpClientErrorException e) {
      responseEntity =
          ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
    }

    return responseEntity;

  }
}
