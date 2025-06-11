package com.devcambo.crudjpa.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
    throws Exception {
    http.authorizeHttpRequests(requests ->
      requests.requestMatchers("/api/v1/auth/**").permitAll().anyRequest().authenticated()
    );
    http.csrf(AbstractHttpConfigurer::disable);
    http.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class);
    http.formLogin(withDefaults());
    http.httpBasic(withDefaults());
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationProvider authenticationProvider
  ) {
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
