package com.devcambo.api.security.exp;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
    HttpServletRequest request,
    HttpServletResponse response,
    AccessDeniedException accessDeniedException
  ) throws IOException, ServletException {
    String message = (
        accessDeniedException != null && accessDeniedException.getMessage() != null
      )
      ? accessDeniedException.getMessage()
      : "Authorization failed";
    log.error("An exception occurred due to : {}", message);
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json;charset=UTF-8");
    String jsonResponse = String.format(
      "{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
      LocalDateTime.now(),
      HttpStatus.FORBIDDEN.value(),
      HttpStatus.FORBIDDEN.getReasonPhrase(),
      message,
      request.getRequestURI()
    );
    response.getWriter().write(jsonResponse);
  }
}
