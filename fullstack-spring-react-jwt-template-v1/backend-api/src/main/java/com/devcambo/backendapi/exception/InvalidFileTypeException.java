package com.devcambo.backendapi.exception;

public class InvalidFileTypeException extends RuntimeException {

  public InvalidFileTypeException(String msg) {
    super(msg);
  }
}
