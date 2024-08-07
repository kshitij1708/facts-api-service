package com.example.facts.cache.policy;

import com.example.facts.cache.Cache;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Least Recently Used (LRU) cache implementation.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
@Component
public class LRUCache<K, V> implements Cache<K, V> {
    @Setter
    private int maxSize = 200;

    private final LinkedHashMap<K, V> cache;

    /**
     * Creates a new LRUCache with the default maximum size.
     */
    public LRUCache() {
        this.cache = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCache.this.maxSize;
            }
        };
    }

    @Override
    public V set(K key, V value) {
        return cache.put(key, value);
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(cache.get(key));
    }

    @Override
    public int size() {
        return cache.size();
    }

}