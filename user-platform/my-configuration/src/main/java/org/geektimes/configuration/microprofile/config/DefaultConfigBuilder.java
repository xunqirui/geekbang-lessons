package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.configuration.microprofile.config.converter.Converters;
import org.geektimes.configuration.microprofile.config.source.ConfigSources;

/**
 * DefaultConfigBuilder
 *
 * @author qrXun on 2021/3/22
 */
public class DefaultConfigBuilder implements ConfigBuilder {

    private final ConfigSources configSources;

    private final Converters converters;

    public DefaultConfigBuilder(ClassLoader classLoader) {
        configSources = new ConfigSources(classLoader);
        converters = new Converters(classLoader);
    }

    @Override
    public ConfigBuilder addDefaultSources() {
        configSources.addDefaultConfigSources();
        return this;
    }

    @Override
    public ConfigBuilder addDiscoveredSources() {
        configSources.addDiscoveredConfigSources();
        return this;
    }

    @Override
    public ConfigBuilder addDiscoveredConverters() {
        converters.addDiscoveredConverters();
        return this;
    }

    @Override
    public ConfigBuilder forClassLoader(ClassLoader loader) {
        configSources.setClassLoader(loader);
        converters.setClassLoader(loader);
        return this;
    }

    @Override
    public ConfigBuilder withSources(ConfigSource... sources) {
        this.configSources.addConfigSources(sources);
        return this;
    }

    @Override
    public ConfigBuilder withConverters(Converter<?>... converters) {
        this.converters.addConverters(converters);
        return this;
    }

    @Override
    public <T> ConfigBuilder withConverter(Class<T> type, int priority, Converter<T> converter) {
        this.converters.addConverter(converter, priority, type);
        return null;
    }

    @Override
    public Config build() {
        return new DefaultConfig(configSources, converters);
    }
}
