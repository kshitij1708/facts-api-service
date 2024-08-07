package com.example.facts.service.impl;

import com.example.facts.cache.wrapper.FactCache;
import com.example.facts.dto.FactDto;
import com.example.facts.event.FactAccessedEvent;
import com.example.facts.event.FactCreatedEvent;
import com.example.facts.exception.DataNotFoundException;
import com.example.facts.model.Fact;
import com.example.facts.repository.FactRepository;
import com.example.facts.service.FactFetchingService;
import com.example.facts.service.FactService;
import com.example.facts.service.UrlShorteningService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link FactService} interface.
 */
@Service
@RequiredArgsConstructor
public class FactServiceImpl implements FactService {

  private final FactFetchingService factFetchingService;
  private final UrlShorteningService urlShorteningService;
  private final FactRepository factRepository;
  private final FactCache factCache;
  private final ApplicationEventPublisher eventPublisher;

  @Value("${app.url.prefix: http://localhost:8080}")
  private String urlPrefix;

  @Override
  public FactDto createFact() {
    Fact fetchedFact = factFetchingService.fetchRandomFact();
    String shortenedUrl = urlShorteningService.generateShortenedUrl();
    Fact storedFact = factRepository.storeFact(shortenedUrl, fetchedFact);

    // Publish the FactCreatedEvent
    eventPublisher.publishEvent(new FactCreatedEvent(this, shortenedUrl));

    return new FactDto(storedFact.getText(), constructFullUrl(shortenedUrl));
  }

  @Override
  public String resolveOriginalUrl(String shortenedUrl) {
    String originalUrl = factCache.get(shortenedUrl) // Check in cache
        .orElseGet(() -> factRepository.getFact(shortenedUrl) // Check in DB, in case of cache miss
            .orElseThrow(() -> new DataNotFoundException(     // Throw exception if not found in both places
                Fact.class.getSimpleName(),
                Map.of("shortenedUrl", shortenedUrl)
            ))
            .getPermalink()
        );

    // Publish the FactAccessedEvent
    eventPublisher.publishEvent(new FactAccessedEvent(this, shortenedUrl, originalUrl));

    return originalUrl;
  }

  @Override
  public boolean existsByShortenedUrl(String shortenedUrl) {
    return factRepository.existsByShortenedUrl(shortenedUrl);
  }

  private String constructFullUrl(String shortenedUrl) {
    return String.format("%s/facts/%s", urlPrefix, shortenedUrl);
  }
}
