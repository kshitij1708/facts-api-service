package com.example.facts.service.impl;

import com.example.facts.exception.ApiRequestException;
import com.example.facts.model.Fact;
import com.example.facts.service.FactFetchingService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of the {@link FactFetchingService} interface.
 */
@Service
@RequiredArgsConstructor
public class FactFetchingServiceImpl implements FactFetchingService {

  private final RestTemplate restTemplate = new RestTemplate();
  private final RetryTemplate retryTemplate;

  @Value("${facts.api.url}")
  private String factsApiUrl;

  @Override
  @RateLimiter(name = "random-fact-service", fallbackMethod = "fallbackMethod")
  public Fact fetchRandomFact() {
    return retryTemplate.execute(context -> {
      try {
        ResponseEntity<Fact> response = restTemplate.getForEntity(factsApiUrl, Fact.class);
        if (response.getStatusCode() != HttpStatus.OK) {
          throw new ApiRequestException("Failed to fetch fact from API", response.getStatusCode());
        }
        return response.getBody();
      } catch (HttpClientErrorException e) {
        handleApiError(e);
        return null;
      }
    });
  }

  private void handleApiError(HttpClientErrorException e) {
    HttpStatusCode statusCode = e.getStatusCode();
    throw new ApiRequestException("API request error: " + e.getMessage(), statusCode);
  }

  private Fact fallbackMethod(RequestNotPermitted requestNotPermitted) {
    throw new ApiRequestException("API request error: Rate limit exceeded", HttpStatus.TOO_MANY_REQUESTS);
  }
}

