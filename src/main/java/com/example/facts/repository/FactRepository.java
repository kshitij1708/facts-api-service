package com.example.facts.repository;

import com.example.facts.model.Fact;
import java.util.Optional;

/**
 * Repository interface for managing facts.
 */
public interface FactRepository {

  /**
   * Stores a fact with the associated shortened URL.
   *
   * @param shortenedUrl the shortened URL
   * @param fact the fact to store
   * @return the stored fact
   */
  Fact storeFact(String shortenedUrl, Fact fact);

  /**
   * Retrieves a fact by its shortened URL.
   *
   * @param shortenedUrl the shortened URL
   * @return the fact, if found
   */
  Optional<Fact> getFact(String shortenedUrl);

  /**
   * Checks if a fact exists by its shortened URL.
   *
   * @param shortenedUrl the shortened URL
   * @return true if the fact exists, false otherwise
   */
  boolean existsByShortenedUrl(String shortenedUrl);

  /**
   * Gets the total number of stored facts.
   *
   * @return the total number of stored facts
   */
  int getTotalRecords();
}
