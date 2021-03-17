package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * DefaultConfig
 *
 * @author qrXun on 2021/3/16
 */
public class DefaultConfig implements Config {

    /**
     * config source list
     */
    private List<ConfigSource> sourceList = new LinkedList<>();

    /**
     * 转换类 map
     */
    private Map<String, Converter<?>> converterMap = new HashMap<>();

    public DefaultConfig() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 读取 config source spi
        ServiceLoader<ConfigSource> serviceLoader = ServiceLoader.load(ConfigSource.class, classLoader);
        serviceLoader.forEach(sourceList::add);
        sourceList.sort(Comparator.comparing(ConfigSource::getOrdinal).reversed());
        // 读取转换器 spi
        ServiceLoader<Converter> converterServiceLoader = ServiceLoader.load(Converter.class, classLoader);
        converterServiceLoader.forEach(converter -> {
            // 获取泛型类型
            ParameterizedType type = (ParameterizedType) converter.getClass().getGenericInterfaces()[0];
            converterMap.put(type.getActualTypeArguments()[0].getTypeName(), converter);
        });

    }

    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        for (ConfigSource configSource : sourceList){
            String configValue = configSource.getValue(propertyName);
            if (configValue != null){
                if (String.class.isAssignableFrom(propertyType)){
                    return (T) configValue;
                }
                Converter<?> converter = converterMap.get(propertyType.getTypeName());
                if (converter == null){
                    throw new RuntimeException("no matching converter found");
                } else {
                    return (T) converter.convert(configValue);
                }
            }
        }
        return null;
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
        Set<String> propertyNames = new HashSet<>();
        sourceList.forEach(configSource -> propertyNames.addAll(configSource.getPropertyNames()));
        return propertyNames;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return sourceList;
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        Converter<T> converter = (Converter<T>) converterMap.get(forType.getTypeName());
        return Optional.ofNullable(converter);
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
