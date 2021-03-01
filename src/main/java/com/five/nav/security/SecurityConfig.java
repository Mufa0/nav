package com.five.nav.security;

import com.five.nav.enums.Role;
import com.five.nav.security.service.SecurityUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private static final String REGISTRATION_LOCATION = "/users/register";

  private static final String ARTICLES_LOCATION = "/articles";
  private static final String UNDER_ARTICLES_LOCATION = "/articles/*";

  private static final String ARTICLE_AUDITS_LOCATION = "/article-audits";
  private static final String UNDER_ARTICLE_AUDITS_LOCATION = "/article-audits/*";


  SecurityUserService securityUserService;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }



  @Bean
  public AuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider =
        new DaoAuthenticationProvider();
    provider.setPasswordEncoder(bCryptPasswordEncoder());
    provider.setUserDetailsService(this.securityUserService);
    return provider;
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf().disable().httpBasic().and()
        .authorizeRequests()
          .antMatchers(REGISTRATION_LOCATION).permitAll()

          .antMatchers(HttpMethod.POST,ARTICLES_LOCATION).hasRole(Role.AUTHOR.toString())
          .antMatchers(HttpMethod.DELETE,UNDER_ARTICLES_LOCATION).hasRole(Role.AUTHOR.toString())
          .antMatchers(HttpMethod.PUT,UNDER_ARTICLES_LOCATION).hasRole(Role.AUTHOR.toString())
          .antMatchers(HttpMethod.GET, ARTICLES_LOCATION).hasAnyRole(Role.AUTHOR.toString(),
            Role.READER.toString())
          .antMatchers(HttpMethod.GET, UNDER_ARTICLES_LOCATION).hasAnyRole(Role.AUTHOR.toString(),
        Role.READER.toString())
          .antMatchers(ARTICLE_AUDITS_LOCATION,UNDER_ARTICLE_AUDITS_LOCATION).hasRole(Role.AUTHOR.toString())
        .anyRequest().authenticated();
  }


}
