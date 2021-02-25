package com.five.nav.controller;

import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/users")
public interface UserControllerInterface {

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  UserResponse registerUser(@Valid @RequestBody UserRequest user);

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  List<UserResponse> getAllUsers();
}
