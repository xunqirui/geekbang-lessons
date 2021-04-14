package org.geektimes.cache.event;

import javax.cache.Cache;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.EventType;

/**
 * GenericCacheEntryEvent
 *
 * @author qrXun on 2021/4/13
 */
public class GenericCacheEntryEvent<K, V> extends CacheEntryEvent<K, V> {

    private final K key;

    private final V oldValue;

    private final V value;

    public GenericCacheEntryEvent(Cache source, EventType eventType, K key, V oldValue, V value) {
        super(source, eventType);
        this.key = key;
        this.oldValue = oldValue;
        this.value = value;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V getOldValue() {
        return oldValue;
    }

    @Override
    public boolean isOldValueAvailable() {
        return oldValue != null;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public <T> T unwrap(Class<T> clazz) {
        return getSource().getCacheManager().unwrap(clazz);
    }

    public static <K, V> CacheEntryEvent<K, V> createdEvent(Cache source, K key, V value) {
        return of(source, EventType.CREATED, key, null, value);
    }

    public static <K, V> CacheEntryEvent<K, V> updatedEvent(Cache source, K key, V oldValue, V value) {
        return of(source, EventType.UPDATED, key, oldValue, value);
    }

    public static <K, V> CacheEntryEvent<K, V> expiredEvent(Cache source, K key, V oldValue) {
        return of(source, EventType.EXPIRED, key, oldValue, oldValue);
    }

    public static <K, V> CacheEntryEvent<K, V> removedEvent(Cache source, K key, V oldValue) {
        return of(source, EventType.REMOVED, key, oldValue, oldValue);
    }

    public static <K, V> CacheEntryEvent<K, V> of(Cache source, EventType eventType, K key, V oldValue, V value) {
        return new GenericCacheEntryEvent<>(source, eventType, key, oldValue, value);
    }
}
