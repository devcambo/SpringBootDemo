package com.devcambo.backendapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

  @Value("${app.jwt.secret}")
  private String jwtSecret;

  @Value("${app.jwt.expiration.minutes}")
  private Long jwtExpirationMinutes;

  public String generateToken(String subject, String roles) {
    Instant now = Instant.now();
    byte[] signingKey = jwtSecret.getBytes();
    return Jwts
      .builder()
      .subject(subject)
      .issuer("https://www.devcambo.com")
      .issuedAt(Date.from(now))
      .expiration(Date.from(now.plusSeconds(60 * jwtExpirationMinutes)))
      .claim("roles", roles)
      .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS256)
      .compact();
  }
}
