package com.five.nav.service;


import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;

public interface UserServiceInterface {

  UserResponse registerUser(UserRequest userRequest);
}
