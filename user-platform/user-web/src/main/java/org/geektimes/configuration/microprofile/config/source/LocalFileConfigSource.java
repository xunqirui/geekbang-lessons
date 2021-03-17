package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * LocalFileConfigSource
 * 本地配置文件配置来源
 * 可通过命令 -Dfile.location.name 来加入外部配置文件的路径，如果没有添加此参数则通过默认参数提供的路径进行兜底
 *
 * @author qrXun on 2021/3/16
 */
public class LocalFileConfigSource implements ConfigSource {

    private static final String CONFIG_SOURCE_NAME = "Local File META-INF/localConfig.properties config source";

    private static final String DEFAULT_FILE_LOCATION = "META-INF/localConfig.properties";

    private static final String JAVA_SYSTEM_FILE_LOCATION_NAME = "file.location.name";

    private static final Integer LOCAL_DEFAULT_ORDINAL = 100;

    private final Map<String, String> propertiesMap;

    public LocalFileConfigSource() {
        propertiesMap = new HashMap<>();
        // 获取 Java 系统属性，如果没有则读取默认 DEFAULT_FILE_LOCATION 作为兜底
        String fileLocation = System.getProperty(JAVA_SYSTEM_FILE_LOCATION_NAME);
        if (fileLocation == null){
            fileLocation = DEFAULT_FILE_LOCATION;
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL propertiesFileURL = classLoader.getResource(fileLocation);
        Properties properties = new Properties();
        try {
            assert propertiesFileURL != null;
            properties.load(propertiesFileURL.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String propertyName : properties.stringPropertyNames()){
            propertiesMap.put(propertyName, properties.getProperty(propertyName));
        }
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
        return LOCAL_DEFAULT_ORDINAL;
    }
}
