package com.example.facts.repository.impl;

import com.example.facts.model.Fact;
import com.example.facts.repository.FactRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the {@link FactRepository}.
 */
@Repository
public class InMemoryFactRepository implements FactRepository {
  private final Map<String, Fact> factsStorage = new ConcurrentHashMap<>();

  @Override
  public Fact storeFact(String shortenedUrl, Fact fact) {
    factsStorage.put(shortenedUrl, fact);
    return fact;
  }

  @Override
  public Optional<Fact> getFact(String shortenedUrl) {
    return Optional.ofNullable(factsStorage.get(shortenedUrl));
  }

  @Override
  public boolean existsByShortenedUrl(String shortenedUrl) {
    return factsStorage.containsKey(shortenedUrl);
  }

  @Override
  public int getTotalRecords() {
    return factsStorage.size();
  }
}
