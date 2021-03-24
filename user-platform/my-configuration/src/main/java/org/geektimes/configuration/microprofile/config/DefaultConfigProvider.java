package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DefaultConfigProvider
 *
 * @author qrXun on 2021/3/17
 */
public class DefaultConfigProvider extends ConfigProviderResolver {

    ConcurrentHashMap<ClassLoader, Config> repositoryMap = new ConcurrentHashMap<>();

    private ClassLoader resolveClassLoader(ClassLoader classLoader) {
        return classLoader == null ? this.getClass().getClassLoader() : classLoader;
    }

    @Override
    public Config getConfig() {
        return getConfig(null);
    }

    @Override
    public Config getConfig(ClassLoader loader) {
        return repositoryMap.computeIfAbsent(loader, this::newConfig);
    }

    private Config newConfig(ClassLoader classLoader) {
        return newConfigBuilder(classLoader).build();
    }

    @Override
    public ConfigBuilder getBuilder() {
        return newConfigBuilder(null);
    }

    protected ConfigBuilder newConfigBuilder(ClassLoader classLoader) {
        return new DefaultConfigBuilder(resolveClassLoader(classLoader));
    }

    @Override
    public void registerConfig(Config config, ClassLoader classLoader) {
        repositoryMap.put(classLoader, config);
    }

    @Override
    public void releaseConfig(Config config) {
        List<ClassLoader> targetKey = new ArrayList<>();
        repositoryMap.forEach((key, repositoryConfig) -> {
            if (Objects.equals(config, repositoryConfig)) {
                targetKey.add(key);
            }
        });
        targetKey.forEach(repositoryMap::remove);
    }
}
