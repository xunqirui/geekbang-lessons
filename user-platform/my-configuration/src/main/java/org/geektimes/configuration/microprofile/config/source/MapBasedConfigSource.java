package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * MapBasedConfigSource
 *
 * @author qrXun on 2021/3/22
 */
public abstract class MapBasedConfigSource implements ConfigSource {

    private final String name;

    private final int ordinal;

    private final Map<String, String> source;

    private boolean preparedProperties = false;

    public MapBasedConfigSource(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
        this.source = getProperties();
    }

    public MapBasedConfigSource(String name, int ordinal, boolean lazyPrepareProperties) {
        this.name = name;
        this.ordinal = ordinal;
        if (!lazyPrepareProperties) {
            this.source = getProperties();
            preparedProperties = true;
        } else {
            this.source = new HashMap<>();
        }

    }


    @Override
    public final Map<String, String> getProperties() {
        Map<String, String> configMap = new HashMap<>();
        prepareProperties(configMap);
        return Collections.unmodifiableMap(configMap);
    }

    /**
     * 准备配置数据
     *
     * @param configData
     */
    protected abstract void prepareProperties(Map configData);

    @Override
    public Set<String> getPropertyNames() {
        judgePrepare();
        return source.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        judgePrepare();
        return source.get(propertyName);
    }

    /**
     * 判断是否已经初始化过 properties
     */
    public void judgePrepare() {
        if (!preparedProperties) {
            Map<String, String> configmap = getProperties();
            preparedProperties = true;
            configmap.forEach(this.source::put);
        }
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }
}
