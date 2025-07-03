package com.devcambo.backendapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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

  public Optional<Jws<Claims>> validateTokenAndGetJws(String token) {
    try {
      byte[] signingKey = jwtSecret.getBytes();

      Jws<Claims> jws = Jwts
        .parser()
        .verifyWith(Keys.hmacShaKeyFor(signingKey))
        .build()
        .parseSignedClaims(token);

      return Optional.of(jws);
    } catch (ExpiredJwtException exception) {
      log.error(
        "Request to parse expired JWT : {} failed : {}",
        token,
        exception.getMessage()
      );
    } catch (UnsupportedJwtException exception) {
      log.error(
        "Request to parse unsupported JWT : {} failed : {}",
        token,
        exception.getMessage()
      );
    } catch (MalformedJwtException exception) {
      log.error(
        "Request to parse invalid JWT : {} failed : {}",
        token,
        exception.getMessage()
      );
    } catch (SignatureException exception) {
      log.error(
        "Request to parse JWT with invalid signature : {} failed : {}",
        token,
        exception.getMessage()
      );
    } catch (IllegalArgumentException exception) {
      log.error(
        "Request to parse empty or null JWT : {} failed : {}",
        token,
        exception.getMessage()
      );
    }
    return Optional.empty();
  }
}
