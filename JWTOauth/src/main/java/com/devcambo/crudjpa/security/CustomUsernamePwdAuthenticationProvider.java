package com.devcambo.crudjpa.security;

import com.devcambo.crudjpa.entity.Role;
import com.devcambo.crudjpa.entity.User;
import com.devcambo.crudjpa.repository.UserRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUsernamePwdAuthenticationProvider implements AuthenticationProvider {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @Override
  public Authentication authenticate(Authentication authentication)
    throws AuthenticationException {
    String username = authentication.getName();
    String pwd = authentication.getCredentials().toString();
    User user = userRepository
      .findByEmail(username)
      .orElseThrow(() ->
        new UsernameNotFoundException("User not found with email: " + username)
      );
    Set<Role> roles = user.getRoles();
    List<SimpleGrantedAuthority> authorities = roles
      .stream()
      .map(role -> new SimpleGrantedAuthority(role.getName()))
      .toList();
    if (encoder.matches(pwd, user.getPassword())) {
      return new UsernamePasswordAuthenticationToken(user, null, authorities);
    } else {
      throw new BadCredentialsException("Invalid password!");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
