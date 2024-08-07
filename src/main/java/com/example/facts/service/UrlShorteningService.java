package com.example.facts.service;

/**
 * Service interface for generating shortened URLs.
 */
public interface UrlShorteningService {

  /**
   * Generates a shortened URL using Base62 and counter.
   *
   * @return the shortened URL as a string
   */
  String generateShortenedUrl();
}
