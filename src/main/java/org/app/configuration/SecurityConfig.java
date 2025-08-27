package org.app.configuration;

import org.app.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtTokenFilter jwtTokenFilter;

  public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
    this.jwtTokenFilter = jwtTokenFilter;
  }

  @Bean
  protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll());
    return http.build();
  }
}