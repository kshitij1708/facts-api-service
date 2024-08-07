package com.example.facts.exception;

import java.util.Map;

public class DataNotFoundException extends RuntimeException {
  public DataNotFoundException(String entityName, Map<String, String> params) {
    super("No data found for " + entityName + " with params: " + params.toString());
  }
}
