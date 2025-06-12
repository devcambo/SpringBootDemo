package com.devcambo.crudjpa.security;

import com.devcambo.crudjpa.constant.JwtConstant;
import com.devcambo.crudjpa.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtService {

  private final Environment env;

  public String generateJwtToken(Authentication authentication) {
    String jwt;
    String secret = env.getProperty(
      JwtConstant.JWT_SECRET,
      JwtConstant.DEFAULT_JWT_SECRET
    );
    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    User fetchedUser = (User) authentication.getPrincipal();
    jwt =
      Jwts
        .builder()
        .issuer("Dev Cambo")
        .subject("JWT Token")
        .claim("username", fetchedUser.getUsername())
        .claim("email", fetchedUser.getEmail())
        .claim(
          "roles",
          authentication
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","))
        )
        .issuedAt(new java.util.Date())
        .expiration(
          new java.util.Date((new java.util.Date()).getTime() + 24 * 60 * 60 * 1000)
        ) // 1 day
        .signWith(secretKey)
        .compact();
    return jwt;
  }
}
