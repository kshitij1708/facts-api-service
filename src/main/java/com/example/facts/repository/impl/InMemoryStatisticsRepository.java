package com.example.facts.repository.impl;

import com.example.facts.repository.StatisticsRepository;
import java.util.ArrayList;
import java.util.Map.Entry;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of the {@link StatisticsRepository}.
 */
@Repository
public class InMemoryStatisticsRepository implements StatisticsRepository {
    private final Map<String, Long> accessStatistics = new ConcurrentHashMap<>();

    @Override
    public void incrementAccessCount(String shortenedUrl) {
        accessStatistics.merge(shortenedUrl, 1L, Long::sum);
    }

    @Override
    public List<Map.Entry<String, Long>> getAccessStatistics() {
        return new ArrayList<>(accessStatistics.entrySet());
    }

    @Override
    public void addNewURL(String shortenedUrl) {
        accessStatistics.put(shortenedUrl, 0L);
    }
}
