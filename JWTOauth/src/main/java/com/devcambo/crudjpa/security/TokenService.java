package com.devcambo.crudjpa.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final JwtEncoder jwtEncoder;

  public String generateToken(String subject, String permissions) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet
      .builder()
      .subject(subject)
      .issuer("https://yourwebsite.com")
      .issuedAt(now)
      .expiresAt(now.plus(1, ChronoUnit.HOURS))
      .claim("scope", permissions)
      .build();
    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
