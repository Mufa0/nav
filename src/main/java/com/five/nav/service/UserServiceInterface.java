package com.five.nav.service;


import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
import java.security.Principal;
import java.util.List;

public interface UserServiceInterface {

  UserResponse registerUser(UserRequest userRequest);

  List<UserResponse> getAllUsers();

  UserResponse getUser( Principal principal);

  UserResponse likeArticle(long id, Principal principal);

  void deleteUser(Principal principal);
}
