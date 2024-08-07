package com.example.facts.event;

import com.example.facts.model.Fact;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event fired when a fact is accessed.
 */
@Getter
public class FactAccessedEvent extends ApplicationEvent {
    private final String shortenedUrl;
    private final String originalUrl;

    /**
     * Creates a new FactAccessedEvent.
     *
     * @param source       the source of the event
     * @param shortenedUrl the shortened URL that was accessed
     * @param originalUrl  the original URL of the @{@link Fact}
     */
    public FactAccessedEvent(Object source, String shortenedUrl, String originalUrl) {
        super(source);
        this.shortenedUrl = shortenedUrl;
        this.originalUrl = originalUrl;
    }
}
