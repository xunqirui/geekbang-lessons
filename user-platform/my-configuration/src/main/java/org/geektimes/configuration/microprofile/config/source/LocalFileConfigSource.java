package org.geektimes.configuration.microprofile.config.source;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * LocalFileConfigSource
 * 本地配置文件配置来源
 * 可通过命令 -Dfile.location.name 来加入外部配置文件的路径，如果没有添加此参数则通过默认参数提供的路径进行兜底
 *
 * @author qrXun on 2021/3/16
 */
public class LocalFileConfigSource extends MapBasedConfigSource {

    private static final String CONFIG_SOURCE_NAME = "Local File META-INF/localConfig.properties config source";

    private static final String DEFAULT_FILE_LOCATION = "META-INF/localConfig.properties";

    private static final String JAVA_SYSTEM_FILE_LOCATION_NAME = "file.location.name";

    private static final Integer LOCAL_DEFAULT_ORDINAL = 100;

    public LocalFileConfigSource() {
        super(CONFIG_SOURCE_NAME, LOCAL_DEFAULT_ORDINAL);
    }

    @Override
    protected void prepareProperties(Map configData) {
        // 获取 Java 系统属性，如果没有则读取默认 DEFAULT_FILE_LOCATION 作为兜底
        String fileLocation = System.getProperty(JAVA_SYSTEM_FILE_LOCATION_NAME);
        if (fileLocation == null) {
            fileLocation = DEFAULT_FILE_LOCATION;
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL propertiesFileURL = classLoader.getResource(fileLocation);
        Properties properties = new Properties();
        try {
            assert propertiesFileURL != null;
            properties.load(propertiesFileURL.openStream());
            configData.putAll(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
