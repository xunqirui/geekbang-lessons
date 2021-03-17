package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * OSSystemConfigSource
 * 系统环境变量配置来源
 *
 * @author qrXun on 2021/3/16
 */
public class OSSystemConfigSource implements ConfigSource {

    private static final String CONFIG_SOURCE_NAME = "os system config source";

    private static final Integer OSS_DEFAULT_ORDINAL = 300;

    private final Map<String, String> propertiesMap;

    public OSSystemConfigSource() {
        propertiesMap = new HashMap<>(System.getenv());
    }

    @Override
    public Set<String> getPropertyNames() {
        return propertiesMap.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return propertiesMap.get(propertyName);
    }

    @Override
    public String getName() {
        return CONFIG_SOURCE_NAME;
    }

    @Override
    public int getOrdinal() {
        String configOrdinal = getValue(CONFIG_ORDINAL);
        if (configOrdinal != null) {
            try {
                return Integer.parseInt(configOrdinal);
            } catch (NumberFormatException ignored) {

            }
        }
        return OSS_DEFAULT_ORDINAL;
    }
}
