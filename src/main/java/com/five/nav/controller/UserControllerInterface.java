package com.five.nav.controller;

import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
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


@RequestMapping("/users")
public interface UserControllerInterface {

  @PostMapping("/register")
  UserResponse registerUser(@Valid @RequestBody UserRequest user);

  @GetMapping
  List<UserResponse> getAllUsers();

  @GetMapping("/user")
  UserResponse getUser( Principal principal);

  @PutMapping("/article/{id}")
  UserResponse likeArticle(@PathVariable("id") long id, Principal principal);

  @DeleteMapping
  void deleteUser(Principal principal);

}
