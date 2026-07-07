package com.wimdeblauwe.thymeleaf_mock_oauth2_login.infrastructure.security;

import jakarta.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {
  @Bean
  @Order(1)
  SecurityFilterChain actuatorFilterChain(HttpSecurity http) {
    return http.securityMatcher("/actuator/**")
        .authorizeHttpRequests(
            registry ->
                registry
                    .requestMatchers(HttpMethod.GET, "/actuator/health/**", "/actuator/info")
                    .permitAll())
        .build();
  }

  @Bean
  @Order(2)
  SecurityFilterChain webSecurityFilterChain(HttpSecurity http) {
    return http.securityMatcher("/**")
        .sessionManagement(
            configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
        .authorizeHttpRequests(
            registry ->
                registry
                    .dispatcherTypeMatchers(DispatcherType.ERROR)
                    .permitAll()
                    .requestMatchers(
                        "/webjars/**",  "/assets/**", "/static/**", "/favicon.ico")
                    .permitAll()
                    .anyRequest()
                    .hasRole(Role.USER.getKeycloakRole()))
        .oauth2Login(
            it ->
                it.userInfoEndpoint(
                    userInfoEndpointConfig ->
                        userInfoEndpointConfig.userAuthoritiesMapper(
                            this::keycloakAuthoritiesMapper)))
        .build();
  }

  private Collection<? extends GrantedAuthority> keycloakAuthoritiesMapper(
      Collection<? extends GrantedAuthority> authorities) {
    List<GrantedAuthority> mappedAuthorities = new ArrayList<>(authorities);

    for (GrantedAuthority authority : authorities) {
      if (authority instanceof OidcUserAuthority oidcUserAuthority) {
        OidcIdToken idToken = oidcUserAuthority.getIdToken();
        return ClaimsToRolesConverter.convert(idToken);
      }
    }

    return mappedAuthorities;
  }
}
