package com.devcambo.crudjpa.security;

import com.devcambo.crudjpa.constant.JwtConstant;
import com.devcambo.crudjpa.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {

  public String generateJwtToken(Authentication authentication) {
    String jwt;
    SecretKey secretKey = Keys.hmacShaKeyFor(
      JwtConstant.JWT_SECRET.getBytes(StandardCharsets.UTF_8)
    );
    User fetchedUser = (User) authentication.getPrincipal();
    log.info("Fetched User: {}", fetchedUser);
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
