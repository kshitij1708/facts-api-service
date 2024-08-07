package com.example.facts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.facts.cache.wrapper.FactCache;
import com.example.facts.dto.FactDto;
import com.example.facts.event.FactAccessedEvent;
import com.example.facts.event.FactCreatedEvent;
import com.example.facts.exception.DataNotFoundException;
import com.example.facts.model.Fact;
import com.example.facts.repository.FactRepository;
import com.example.facts.service.impl.FactServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

class FactServiceImplTest {

  @Mock
  private FactFetchingService factFetchingService;

  @Mock
  private UrlShorteningService urlShorteningService;

  @Mock
  private FactRepository factRepository;

  @Mock
  private FactCache factCache;

  @Mock
  private ApplicationEventPublisher eventPublisher;

  @InjectMocks
  private FactServiceImpl factService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateFact() {
    // Arrange
    Fact fetchedFact = new Fact("123", "A random fact", "source", "http://source.com", "en", "http://fact.com/123");
    when(factFetchingService.fetchRandomFact()).thenReturn(fetchedFact);
    when(urlShorteningService.generateShortenedUrl()).thenReturn("shortUrl123");
    when(factRepository.storeFact(anyString(), any(Fact.class))).thenReturn(fetchedFact);

    // Act
    FactDto result = factService.createFact();

    // Assert
    assertNotNull(result);
    assertEquals("A random fact", result.originalFact());
    assertTrue(result.shortenedUrl().contains("shortUrl123"));

    ArgumentCaptor<FactCreatedEvent> eventCaptor = ArgumentCaptor.forClass(FactCreatedEvent.class);
    verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
    FactCreatedEvent event = eventCaptor.getValue();
    assertEquals("shortUrl123", event.getShortenedUrl());
  }

  @Test
  void testResolveOriginalUrl_CacheHit() {
    // Arrange
    when(factCache.get("shortUrl123")).thenReturn(Optional.of("http://fact.com/123"));

    // Act
    String result = factService.resolveOriginalUrl("shortUrl123");

    // Assert
    assertNotNull(result);
    assertEquals("http://fact.com/123", result);
    verify(eventPublisher, times(1)).publishEvent(any(FactAccessedEvent.class));
    verify(factRepository, never()).getFact(anyString());
  }

  @Test
  void testResolveOriginalUrl_CacheMiss_DBHit() {
    // Arrange
    Fact fetchedFact = new Fact("123", "A random fact", "source", "http://source.com", "en", "http://fact.com/123");
    when(factCache.get("shortUrl123")).thenReturn(Optional.empty());
    when(factRepository.getFact("shortUrl123")).thenReturn(Optional.of(fetchedFact));

    // Act
    String result = factService.resolveOriginalUrl("shortUrl123");

    // Assert
    assertNotNull(result);
    assertEquals("http://fact.com/123", result);
    verify(eventPublisher, times(1)).publishEvent(any(FactAccessedEvent.class));
  }

  @Test
  void testResolveOriginalUrl_NotFound() {
    // Arrange
    when(factCache.get("shortUrl123")).thenReturn(Optional.empty());
    when(factRepository.getFact("shortUrl123")).thenReturn(Optional.empty());

    // Act & Assert
    DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
      factService.resolveOriginalUrl("shortUrl123");
    });

    assertNotNull(exception);
    assertEquals("No data found for Fact with params: {shortenedUrl=shortUrl123}", exception.getMessage());
  }

  @Test
  void testExistsByShortenedUrl() {
    // Arrange
    when(factRepository.existsByShortenedUrl("shortUrl123")).thenReturn(true);

    // Act
    boolean result = factService.existsByShortenedUrl("shortUrl123");

    // Assert
    assertTrue(result);
  }
}
