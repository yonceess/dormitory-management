package com.diploma.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  private final SimpleAuthenticationSuccessHandler successHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .requestMatchers("/admin/**", "/file/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**", "/file/user/**").hasRole("USER")
            .requestMatchers("/api/v1/**", "/*/*.css","/fonts/**", "/*/*.js", "vendors/**", "/*/*.ico",  "/*/*.png", "/*/*.jpg"
            , "/*/*.min.css", "/*/*/*.min.js")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/api/v1/auth/login").successHandler(successHandler)
            .permitAll()
            .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

            .logout()
            .logoutSuccessUrl("/api/v1/auth/main")
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler((request, response, authentication) -> {
              SecurityContextHolder.clearContext();
              response.sendRedirect("/api/v1/auth/main");
            }) ;

    return http.build();
  }
}
