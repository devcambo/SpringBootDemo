package com.devcambo.crudjpa.exception;

import com.devcambo.crudjpa.dto.ErrorResponseDto;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleGlobalException(
    Exception exception,
    WebRequest webRequest
  ) {
    log.error("An exception occurred due to : {}", exception.getMessage());
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
      webRequest.getDescription(false),
      HttpStatus.INTERNAL_SERVER_ERROR,
      exception.getMessage(),
      LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
    MethodArgumentNotValidException exception
  ) {
    log.error("An exception occurred due to : {}", exception.getMessage());
    Map<String, String> errors = new HashMap<>();
    List<FieldError> fieldErrorList = exception.getBindingResult().getFieldErrors();
    fieldErrorList.forEach(error ->
      errors.put(error.getField(), error.getDefaultMessage())
    );
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
    ResourceNotFoundException exception,
    WebRequest webRequest
  ) {
    log.error("An exception occurred due to : {}", exception.getMessage());
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
      webRequest.getDescription(false),
      HttpStatus.NOT_FOUND,
      exception.getMessage(),
      LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
  public ResponseEntity<ErrorResponseDto> handleAuthenticationException(
    Exception exception,
    WebRequest webRequest
  ) {
    log.error("An exception occurred due to : {}", exception.getMessage());
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
      webRequest.getDescription(false),
      HttpStatus.UNAUTHORIZED,
      exception.getMessage(),
      LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
  }
}
