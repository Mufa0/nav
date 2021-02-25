package com.five.nav.unit;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.five.nav.domain.User;
import com.five.nav.mapper.UserMapper;
import com.five.nav.repository.UserRepository;
import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
import com.five.nav.service.UserServiceInterface;
import com.five.nav.service.implementation.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserMapperUnitTest {

  UserMapper userMapper;

  BCryptPasswordEncoder bCryptPasswordEncoder;

  @BeforeEach
  private void init(){

    bCryptPasswordEncoder = new BCryptPasswordEncoder();
    userMapper = new UserMapper(bCryptPasswordEncoder);

  }
  @Test
  void roleNotDefinedShouldReturnRoleReader(){
    UserRequest request = UserRequest.builder().name("Test").lastname("Test").email("test@test"
        + ".com").password("pass").build();

    User user = userMapper.from(request);

    assertThat(user.getRole().toString()).isEqualTo("READER");
  }

  @Test void roleDefinedShoulsReturnRole(){
    String role = "AUTHOR";
    UserRequest request = UserRequest.builder().name("Test").lastname("Test").email("test@test"
        + ".com").password("pass").role(role).build();

    User user = userMapper.from(request);

    assertThat(user.getRole().toString()).isEqualTo(role);
  }
}
