package com.example.facts.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event fired when a fact is created.
 */
@Getter
public class FactCreatedEvent extends ApplicationEvent {
  /**
   * -- GETTER --
   *  Gets the shortened URL that was created.
   *
   * @return the shortened URL
   */
  private final String shortenedUrl;

  /**
   * Creates a new FactCreatedEvent.
   *
   * @param source the source of the event
   * @param shortenedUrl the shortened URL that was created
   */
  public FactCreatedEvent(Object source, String shortenedUrl) {
    super(source);
    this.shortenedUrl = shortenedUrl;
  }

}
