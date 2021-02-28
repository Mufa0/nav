package com.five.nav.unit;


import static org.assertj.core.api.Assertions.assertThat;

import com.five.nav.domain.User;
import com.five.nav.mapper.ArticleMapper;
import com.five.nav.mapper.UserMapper;
import com.five.nav.request.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
