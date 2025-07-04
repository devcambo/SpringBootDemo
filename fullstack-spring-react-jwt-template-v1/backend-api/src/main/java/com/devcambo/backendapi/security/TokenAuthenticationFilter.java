package com.devcambo.backendapi.security;

import com.devcambo.backendapi.constant.AppConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private final AntPathMatcher pathMatcher = new AntPathMatcher();
  private final TokenProvider tokenProvider;
  private final List<String> publicPaths;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      log.info("Filtering request");
      getJwtFromRequest(request)
        .flatMap(tokenProvider::validateTokenAndGetJws)
        .ifPresent(jws -> {
          String subject = jws.getPayload().getSubject();
          String roles = jws.getPayload().get("roles").toString();
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            subject,
            null,
            AuthorityUtils.commaSeparatedStringToAuthorityList(roles)
          );
          authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
          );
          SecurityContextHolder.getContext().setAuthentication(authentication);
        });
    } catch (Exception ex) {
      log.error("Cannot set user authentication", ex);
    }
    filterChain.doFilter(request, response);
  }

  private Optional<String> getJwtFromRequest(HttpServletRequest request) {
    String tokenHeader = request.getHeader(AppConstants.TOKEN_HEADER);
    log.info("token header: {}", tokenHeader);
    if (
      StringUtils.hasText(tokenHeader) &&
      tokenHeader.startsWith(AppConstants.TOKEN_PREFIX)
    ) {
      return Optional.of(tokenHeader.replace(AppConstants.TOKEN_PREFIX, ""));
    }
    return Optional.empty();
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return publicPaths
      .stream()
      .anyMatch(publicPath -> pathMatcher.match(publicPath, path));
  }
}
