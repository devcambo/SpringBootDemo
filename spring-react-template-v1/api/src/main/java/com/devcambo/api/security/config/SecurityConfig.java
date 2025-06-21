package com.devcambo.api.security.config;

import com.devcambo.api.security.exp.CustomAccessDeniedHandler;
import com.devcambo.api.security.exp.CustomAuthenticationEntryPoint;
import com.devcambo.api.security.jwt.JWTTokenValidatorFilter;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JWTTokenValidatorFilter jwtTokenValidatorFilter;

  @Value("${cors-allowed-origins}")
  private String allowedOrigins;

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
    throws Exception {
    return http
      .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/api/v1/auth/**")
          .permitAll()
          .requestMatchers("/api/v1/admin/**")
          .hasRole("ADMIN")
          .anyRequest()
          .authenticated()
      )
      .addFilterBefore(jwtTokenValidatorFilter, BasicAuthenticationFilter.class)
      .csrf(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .logout(AbstractHttpConfigurer::disable)
      .rememberMe(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .exceptionHandling(ex ->
        ex
          .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
          .accessDeniedHandler(new CustomAccessDeniedHandler())
      )
      .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationProvider authenticationProvider
  ) {
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of(allowedOrigins));
    config.setAllowedMethods(Collections.singletonList("*"));
    config.setAllowedHeaders(Collections.singletonList("*"));
    config.setAllowCredentials(true);
    config.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
