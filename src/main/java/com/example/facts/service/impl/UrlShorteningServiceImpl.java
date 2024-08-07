package com.example.facts.service.impl;

import com.example.facts.service.UrlShorteningService;
import com.example.facts.util.Base62Encoder;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {

  private static final AtomicInteger counter = new AtomicInteger(0);

  @Override
  public synchronized String generateShortenedUrl() {
    long currentCounterValue = counter.incrementAndGet();
    return Base62Encoder.encode(BigInteger.valueOf(currentCounterValue));
  }
}
