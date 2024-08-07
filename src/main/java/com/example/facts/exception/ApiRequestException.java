package com.example.facts.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ApiRequestException extends RuntimeException {

  private final HttpStatusCode statusCode;

  public ApiRequestException(String message, HttpStatusCode statusCode) {
    super(message);
    this.statusCode = statusCode;
  }
}
