package com.devcambo.api.security.jwt;

import com.devcambo.api.constant.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final Environment env;

  public String generateJwtToken(String subject, String roles) {
    return Jwts
      .builder()
      .subject(subject)
      .issuer("https://www.devcambo.com")
      .issuedAt(new java.util.Date())
      .expiration(
        new java.util.Date((new java.util.Date()).getTime() + AppConstants.JWT_EXPIRATION)
      )
      .claim("roles", roles)
      .signWith(getSecretKey())
      .compact();
  }

  public Claims getJwtClaims(String jwtToken) {
    return Jwts
      .parser()
      .verifyWith(getSecretKey())
      .build()
      .parseSignedClaims(jwtToken)
      .getPayload();
  }

  private SecretKey getSecretKey() {
    String secret = env.getProperty(
      AppConstants.JWT_SECRET,
      AppConstants.DEFAULT_JWT_SECRET
    );
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }
}
