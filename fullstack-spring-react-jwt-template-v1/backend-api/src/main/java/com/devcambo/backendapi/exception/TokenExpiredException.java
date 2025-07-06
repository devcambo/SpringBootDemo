package com.devcambo.backendapi.exception;

public class TokenExpiredException extends RuntimeException {

  public TokenExpiredException(String msg) {
    super(msg);
  }
}
