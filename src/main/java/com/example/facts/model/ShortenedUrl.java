package com.example.facts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenedUrl {
  private String originalFact;
  private String shortenedUrl;
}