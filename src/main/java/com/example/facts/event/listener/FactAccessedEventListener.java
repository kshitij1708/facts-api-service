package com.example.facts.event.listener;

import com.example.facts.cache.wrapper.FactCache;
import com.example.facts.event.FactAccessedEvent;
import com.example.facts.service.FactStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Event listener for handling fact accessed events.
 */
@Component
@RequiredArgsConstructor
public class FactAccessedEventListener {

    private final FactStatisticsService factStatisticsService;
    private final FactCache factCache;

    /**
     * Handles the {@link FactAccessedEvent} to increment access counts asynchronously.
     *
     * @param event the fact accessed event
     */
    @Async
    @EventListener
    public void handleFactAccessedEvent(FactAccessedEvent event) {
        factStatisticsService.incrementAccess(event.getShortenedUrl());
        factCache.set(event.getShortenedUrl(), event.getOriginalUrl());
    }
}
