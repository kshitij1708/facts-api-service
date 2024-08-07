package com.example.facts.service;

import com.example.facts.model.Fact;

/**
 * Service interface for fetching random facts.
 */
public interface FactFetchingService {

  /**
   * Fetches a random fact from an external API.
   *
   * @return the fetched fact
   */
  Fact fetchRandomFact();
}
