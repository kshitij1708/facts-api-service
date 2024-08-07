package com.example.facts.service;

import com.example.facts.dto.StatisticsDto;
import java.util.List;

/**
 * Service interface for managing fact statistics.
 */
public interface FactStatisticsService {

  /**
   * Increments the access count for a given shortened URL.
   *
   * @param shortenedUrl the shortened URL
   */
  void incrementAccess(String shortenedUrl);

  /**
   * Retrieves the access statistics for all shortened URLs.
   *
   * @return a list of statistics DTOs
   */
  List<StatisticsDto> getStatistics();


  /**
   * Add a new url to stats database.
   *
   * @return a list of statistics DTOs
   */
  void addNewFact(String shortenedUrl);
}
