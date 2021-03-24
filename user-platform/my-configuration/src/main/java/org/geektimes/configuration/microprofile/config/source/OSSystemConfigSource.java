package org.geektimes.configuration.microprofile.config.source;

import java.util.Map;

/**
 * OSSystemConfigSource
 * 系统环境变量配置来源
 *
 * @author qrXun on 2021/3/16
 */
public class OSSystemConfigSource extends MapBasedConfigSource{

    private static final String CONFIG_SOURCE_NAME = "os system config source";

    private static final Integer OSS_DEFAULT_ORDINAL = 300;

    public OSSystemConfigSource() {
        super(CONFIG_SOURCE_NAME, OSS_DEFAULT_ORDINAL);
    }

    @Override
    protected void prepareProperties(Map configData) {
        configData.putAll(System.getenv());
    }
}
