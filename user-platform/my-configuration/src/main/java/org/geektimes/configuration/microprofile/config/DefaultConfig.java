package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.configuration.microprofile.config.converter.Converters;
import org.geektimes.configuration.microprofile.config.source.ConfigSources;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

/**
 * DefaultConfig
 *
 * @author qrXun on 2021/3/16
 */
public class DefaultConfig implements Config {

    private final ConfigSources configSources;

    private final Converters converters;

    public DefaultConfig(ConfigSources configSources, Converters converters) {
        this.configSources = configSources;
        this.converters = converters;
    }

    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        String propertyValueStr = getPropertyValueStr(propertyName);
        Converter<T> converter = doGetConverter(propertyType);
        return converter == null ? null : converter.convert(propertyValueStr);
    }

    public String getPropertyValueStr(String propertyName) {
        String propertyValueStr = null;
        for (ConfigSource configSource : configSources) {
            String configValue = configSource.getValue(propertyName);
            if (configValue != null){
                propertyValueStr = configValue;
                break;
            }
        }
        return propertyValueStr;
    }

    @Override
    public ConfigValue getConfigValue(String propertyName) {
        return null;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        return Optional.ofNullable(getValue(propertyName, propertyType));
    }

    @Override
    public Iterable<String> getPropertyNames() {
        return StreamSupport.stream(configSources.spliterator(), false)
                .map(ConfigSource::getPropertyNames)
                .collect(LinkedHashSet::new, Set::addAll, Set::addAll);
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return configSources;
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        Converter<T> converter = doGetConverter(forType);
        return converter == null ? Optional.empty() : Optional.of(converter);
    }

    protected <T> Converter<T> doGetConverter(Class<T> forType){
        List<Converter> typeConverters = converters.getConverters(forType);
        return typeConverters.isEmpty() ? null : typeConverters.get(0);
    }

    /**
     * 这个不太清楚要返回什么，看网上说可以允许返回一些有必要的实现
     *
     * @param type
     * @param <T>
     * @return
     */
    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }
}
