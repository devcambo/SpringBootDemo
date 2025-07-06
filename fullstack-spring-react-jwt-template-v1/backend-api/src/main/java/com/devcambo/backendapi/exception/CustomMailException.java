package com.devcambo.backendapi.exception;

public class CustomMailException extends RuntimeException {

  public CustomMailException(String msg) {
    super(msg);
  }
}
