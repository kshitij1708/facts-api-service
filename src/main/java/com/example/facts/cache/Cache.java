package com.example.facts.cache;

import java.util.Optional;

/**
 * Cache interface defining basic operations for a cache.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public interface Cache<K, V> {

  /**
   * Sets a value in the cache.
   *
   * @param key the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   * @return the previous value associated with the specified key, or null if there was no mapping for the key
   */
  V set(K key, V value);

  /**
   * Gets a value from the cache.
   *
   * @param key the key whose associated value is to be returned
   * @return an {@link Optional} describing the value associated with the specified key, or an empty {@link Optional} if there is no mapping for the key
   */
  Optional<V> get(K key);

  /**
   * Returns the number of key-value mappings in this cache.
   *
   * @return the number of key-value mappings in this cache
   */
  int size();

  /**
   * Sets the maximum size of the cache.
   *
   * @param maxCacheSize the maximum size of the cache
   */
  void setMaxSize(int maxCacheSize);
}