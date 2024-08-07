package com.example.facts.cache.wrapper;

import com.example.facts.cache.Cache;
import com.example.facts.repository.FactRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Wrapper around the cache for managing facts with adjustable size.
 */
@Component
@RequiredArgsConstructor
public class FactCache {

  @Value("${facts.cache.capacity.factor:0.2f}")
  private float cacheCapacityFactor;

  private final Cache<String, String> cache;
  private final FactRepository factRepository;

  /**
   * Sets a fact in the cache.
   *
   * @param shortenedUrl the shortened URL
   * @param originalUrl the original URL
   * @return the previous value associated with the specified key, or null if there was no mapping for the key
   */
  public String set(String shortenedUrl, String originalUrl) {
    return cache.set(shortenedUrl, originalUrl);
  }

  /**
   * Gets a fact from the cache.
   *
   * @param shortenedUrl the shortened URL
   * @return an {@link Optional} describing the value associated with the specified key, or an empty {@link Optional} if there is no mapping for the key
   */
  public Optional<String> get(String shortenedUrl) {
    return cache.get(shortenedUrl);
  }

  /**
   * Adjusts the size of the cache based on the current number of stored facts and the cache capacity factor.
   */
  public void adjustCacheSize() {
    int totalRecords = factRepository.getTotalRecords();
    int maxCacheSize = (int) (totalRecords * cacheCapacityFactor);
    cache.setMaxSize(maxCacheSize);
  }
}
