package com.devcambo.backendapi.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException authException
  ) throws IOException, ServletException {
    String message = (authException != null && authException.getMessage() != null)
      ? authException.getMessage()
      : "Unauthorized";
    log.error("An exception occurred due to : {}", message);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json;charset=UTF-8");
    String jsonResponse = String.format(
      "{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
      LocalDateTime.now(),
      HttpStatus.UNAUTHORIZED.value(),
      HttpStatus.UNAUTHORIZED.getReasonPhrase(),
      message,
      request.getRequestURI()
    );
    response.getWriter().write(jsonResponse);
  }
}
