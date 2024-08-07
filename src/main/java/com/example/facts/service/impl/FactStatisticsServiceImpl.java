package com.example.facts.service.impl;

import com.example.facts.dto.StatisticsDto;
import com.example.facts.exception.DataNotFoundException;
import com.example.facts.model.Fact;
import com.example.facts.repository.StatisticsRepository;
import com.example.facts.service.FactService;
import com.example.facts.service.FactStatisticsService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link FactStatisticsService} interface.
 */
@Service
@RequiredArgsConstructor
public class FactStatisticsServiceImpl implements FactStatisticsService {

  private final FactService factService;
  private final StatisticsRepository statisticsRepository;

  @Override
  public void incrementAccess(String shortenedUrl) {
    if (!factService.existsByShortenedUrl(shortenedUrl)) {
      throw new DataNotFoundException(Fact.class.getSimpleName(), Map.of("shortenedUrl", shortenedUrl));
    }
    statisticsRepository.incrementAccessCount(shortenedUrl);
  }

  @Override
  public List<StatisticsDto> getStatistics() {
    return statisticsRepository.getAccessStatistics().stream()
        .sorted(Comparator.comparingLong((Entry<String, Long> entry) -> entry.getValue()).reversed())
        .map(entry -> new StatisticsDto(entry.getKey(), entry.getValue()))
        .toList();
  }

  @Override
  public void addNewFact(String shortenedUrl) {
      statisticsRepository.addNewURL(shortenedUrl);
  }
}
