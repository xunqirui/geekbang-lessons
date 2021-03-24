package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.*;

import static java.util.stream.Stream.of;

/**
 * ConfigSources
 *
 * @author qrXun on 2021/3/22
 */
public class ConfigSources implements Iterable<ConfigSource> {

    private boolean addedDefaultConfigSources;

    private boolean addedDiscoveredConfigSources;

    private List<ConfigSource> configSources = new LinkedList<>();

    private ClassLoader classLoader;

    public ConfigSources() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public ConfigSources(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * 增加默认配置来源
     */
    public void addDefaultConfigSources() {
        if (addedDefaultConfigSources) {
            return;
        }
        addConfigSources(
                LocalFileConfigSource.class,
                OSSystemConfigSource.class
        );
        addedDefaultConfigSources = true;
    }

    /**
     * 增加 spi 配置来源
     */
    public void addDiscoveredConfigSources() {
        if (addedDiscoveredConfigSources) {
            return;
        }
        addConfigSources(ServiceLoader.load(ConfigSource.class, classLoader));
        addedDiscoveredConfigSources = true;
    }

    public void addConfigSources(Class<? extends ConfigSource>... configSourceClasses) {
        addConfigSources(
                of(configSourceClasses)
                        .map(this::newInstance)
                        .toArray(ConfigSource[]::new)
        );
    }

    public void addConfigSources(ConfigSource[] configSourceArray) {
        addConfigSources(Arrays.asList(configSourceArray));
    }

    public void addConfigSources(Iterable<ConfigSource> configSourceList) {
        configSourceList.forEach(this.configSources::add);
        this.configSources.sort(ConfigSourceOrdinalComparator.INSTANCE);
    }

    private ConfigSource newInstance(Class<? extends ConfigSource> confSourceClass) {
        ConfigSource instance = null;
        try {
            instance = confSourceClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    @Override
    public Iterator<ConfigSource> iterator() {
        return configSources.iterator();
    }
}
