package com.example.facts.exception.handler;

import com.example.facts.dto.ErrorDto;
import com.example.facts.exception.ApiRequestException;
import com.example.facts.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<ErrorDto> handleDataNotFoundException(DataNotFoundException ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    ErrorDto errorDetails = new ErrorDto(ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    ErrorDto errorDetails = new ErrorDto(ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleGlobalException(Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    ErrorDto errorDetails = new ErrorDto(ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ApiRequestException.class)
  public ResponseEntity<ErrorDto> handleApiRequestException(ApiRequestException ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    ErrorDto errorDetails = new ErrorDto(ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, ex.getStatusCode());
  }
}
