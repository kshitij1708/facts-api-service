package com.example.facts.event.listener;

import com.example.facts.cache.wrapper.FactCache;
import com.example.facts.event.FactCreatedEvent;
import com.example.facts.service.FactStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Event listener for handling fact created events.
 */
@Component
@RequiredArgsConstructor
public class FactCreatedEventListener {

  private final FactCache factCache;
  private final FactStatisticsService factStatisticsService;

  /**
   * Handles the {@link FactCreatedEvent} to resize the cache asynchronously.
   *
   * @param event the fact created event
   */
  @Async
  @EventListener
  public void handleFactCreatedEvent(FactCreatedEvent event) {
    resizeCache();
    populateStats(event.getShortenedUrl());
  }

  /**
   * Populate newly created url into stats db.
   */
  private void populateStats(String shortenedUrl) {
    factStatisticsService.addNewFact(shortenedUrl);
  }

  /**
   * Resizes the fact cache based on the current number of stored facts.
   */
  private void resizeCache() {
    factCache.adjustCacheSize();
  }
}
