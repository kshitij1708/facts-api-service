package com.example.facts.service;

import com.example.facts.dto.FactDto;

/**
 * Service interface for managing facts.
 */
public interface FactService {

  /**
   * Creates a new fact and generates a shortened URL.
   *
   * @return the DTO containing the original fact and the shortened URL
   */
  FactDto createFact();

  /**
   * Resolves the original URL for the given shortened URL.
   *
   * @param shortenedUrl the shortened URL
   * @return the original URL
   */
  String resolveOriginalUrl(String shortenedUrl);

  /**
   * Checks if a fact exists for the given shortened URL.
   *
   * @param shortenedUrl the shortened URL
   * @return true if the fact exists, false otherwise
   */
  boolean existsByShortenedUrl(String shortenedUrl);
}
