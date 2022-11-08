package com.minu.pokedoc.config.auth;

import com.minu.pokedoc.domain.user.Role;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.h2.util.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

   http
    .csrf().disable()
    .headers().frameOptions().disable()
    .and()
      .authorizeRequests()
      .antMatchers("/", "/css/**", "/image/**", "/js/**", "/h2-console/**", "/halloween/**", "/hello/**").permitAll()
      .antMatchers("/api/**").hasAnyRole(Role.GENERAL.name(),Role.ADMIN.name())
      .anyRequest().authenticated()
    .and()
      .logout()
        .logoutSuccessUrl("/")
    .and()
      .oauth2Login()
        .userInfoEndpoint()
          .userService(customOAuth2UserService);

   return http.build();
  }
}
