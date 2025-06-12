package com.devcambo.crudjpa.security;

import com.devcambo.crudjpa.constant.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    String authHeader = request.getHeader(JwtConstant.JWT_HEADER);
    if (authHeader != null) {
      try {
        String jwt = authHeader.substring(7);
        Environment env = getEnvironment();
        if (env != null) {
          String secret = env.getProperty(
            JwtConstant.JWT_SECRET,
            JwtConstant.DEFAULT_JWT_SECRET
          );
          SecretKey secretKey = Keys.hmacShaKeyFor(
            secret.getBytes(StandardCharsets.UTF_8)
          );
          if (secretKey != null) {
            Claims claims = Jwts
              .parser()
              .verifyWith(secretKey)
              .build()
              .parseSignedClaims(jwt)
              .getPayload();
            String username = String.valueOf(claims.get("email"));
            String roles = String.valueOf(claims.get("roles"));
            Authentication authentication = new UsernamePasswordAuthenticationToken(
              username,
              null,
              AuthorityUtils.commaSeparatedStringToAuthorityList(roles)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }
      } catch (ExpiredJwtException exception) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Token Expired");
        return;
      } catch (Exception exception) {
        throw new BadCredentialsException("Invalid Token received!");
      }
    }
    filterChain.doFilter(request, response);
  }
}
