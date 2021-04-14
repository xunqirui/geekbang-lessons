package org.geektimes.cache.event;

import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.event.CacheEntryEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * CacheEntryEventPublisher
 *
 * @author qrXun on 2021/4/13
 */
public class CacheEntryEventPublisher {

    private List<ConditionalCacheEntryEventListener> listeners = new LinkedList<>();

    public void registerCacheEntryListener(CacheEntryListenerConfiguration configuration){
        CacheEntryEventListenerAdapter listenerAdapter = new CacheEntryEventListenerAdapter(configuration);
        listeners.add(listenerAdapter);
    }

    public void deregisterCacheEntryListener(CacheEntryListenerConfiguration configuration){
        CacheEntryEventListenerAdapter listenerAdapter = new CacheEntryEventListenerAdapter(configuration);
        listeners.remove(listenerAdapter);
    }

    public <K, V> void publish(CacheEntryEvent<? extends K, ? extends V> entryEvent){
        listeners.forEach(listeners -> listeners.onEvent(entryEvent));
    }

}
