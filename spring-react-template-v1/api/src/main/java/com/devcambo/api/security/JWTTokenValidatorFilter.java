package com.devcambo.api.security;

import com.devcambo.api.constant.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

  private final TokenService tokenService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    String authHeader = request.getHeader(AppConstants.JWT_HEADER);
    if (authHeader != null) {
      try {
        String jwtToken = authHeader.substring(7);
        Claims jwtClaims = tokenService.getJwtClaims(jwtToken);
        String subject = String.valueOf(jwtClaims.getSubject());
        String roles = String.valueOf(jwtClaims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
          subject,
          null,
          AuthorityUtils.commaSeparatedStringToAuthorityList(roles)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (ExpiredJwtException exception) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Token Expired");
        return;
      } catch (Exception exception) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Invalid Token received!");
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

}
