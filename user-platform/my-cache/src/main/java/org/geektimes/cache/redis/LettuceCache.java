package org.geektimes.cache.redis;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.geektimes.cache.AbstractCache;
import org.geektimes.cache.ExpirableEntry;
import org.geektimes.serialize.Serializing;
import org.geektimes.serialize.SerializingGetting;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.util.Set;

/**
 * LettuceCache
 *
 * @author qrXun on 2021/4/14
 */
public class LettuceCache<K, V> extends AbstractCache<K, V> {

    private final RedisCommands<byte[], byte[]> syncCommands;

    private final Serializing<byte[]> serializing;

    public LettuceCache(CacheManager cacheManager,
                        String cacheName,
                        Configuration<K, V> configuration,
                        StatefulRedisConnection<byte[], byte[]> connection) {
        super(cacheManager, cacheName, configuration);
        this.syncCommands = connection.sync();
        this.serializing = SerializingGetting.getSerializingProvider().getSerializing();
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serializing.serialize(key);
        return syncCommands.exists(keyBytes) > 0;
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serializing.serialize(key);
        return getEntry(keyBytes);
    }

    private ExpirableEntry<K, V>  getEntry(byte[] keyBytes) {
        byte[] valueByte = syncCommands.get(keyBytes);
        return ExpirableEntry.of(serializing.deserialize(keyBytes), serializing.deserialize(valueByte));
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        byte[] keyBytes = serializing.serialize(entry.getKey());
        byte[] valueBytes = serializing.serialize(entry.getValue());
        syncCommands.set(keyBytes, valueBytes);
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serializing.serialize(key);
        ExpirableEntry<K, V> expirableEntry = getEntry(keyBytes);
        syncCommands.del(keyBytes);
        return expirableEntry;
    }

    @Override
    protected void clearEntries() throws CacheException {

    }

    @Override
    protected Set<K> keySet() {
        return null;
    }
}
