package com.example.facts.repository;

import java.util.List;
import java.util.Map;


/**
 * Repository interface for managing access statistics.
 */
public interface StatisticsRepository {

  /**
   * Increments the access count for a given shortened URL.
   *
   * @param shortenedUrl the shortened URL
   */
  void incrementAccessCount(String shortenedUrl);

  /**
   * Retrieves the access statistics for all shortened URLs.
   *
   * @return a list of map of shortened URLs to their access counts
   */
  List<Map.Entry<String, Long>> getAccessStatistics();


  /**
   * Add a new URL and initialize count to 0.
   */
  void addNewURL(String shortenedUrl);
}
