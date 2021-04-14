package org.geektimes.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import org.geektimes.cache.AbstractCacheManager;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Optional;
import java.util.Properties;

/**
 * LettuceCacheManager
 *
 * @author qrXun on 2021/4/14
 */
public class LettuceCacheManager extends AbstractCacheManager {

    private final RedisClient redisClient;

    private final StatefulRedisConnection connection;


    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
        redisClient = RedisClient.create(getRedisURI(uri));
        connection = redisClient.connect(new ByteArrayCodec());
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        return new LettuceCache(this, cacheName, configuration, connection);
    }

    @Override
    protected void doClose() {
        connection.close();
        redisClient.shutdown();
    }

    private RedisURI getRedisURI(URI uri){
        int dataBase = 0;
        if (uri.getPath() != null && uri.getPath().split("/").length > 0){
            dataBase = Integer.parseInt(uri.getPath().split("/")[1]);
        }
        return RedisURI.builder()
                .withHost(Optional.ofNullable(uri.getHost()).orElse("localhost"))
                .withPort(uri.getPort() == -1 ? 6379 : uri.getPort())
                .withDatabase(dataBase)
                .build();
    }
}
