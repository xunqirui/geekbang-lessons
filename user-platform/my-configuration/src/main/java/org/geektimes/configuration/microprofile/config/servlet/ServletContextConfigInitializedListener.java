package org.geektimes.configuration.microprofile.config.servlet;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.geektimes.configuration.microprofile.config.source.ServletConfigSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ConfigServletListener
 *
 * @author qrXun on 2021/3/23
 */
public class ServletContextConfigInitializedListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ClassLoader classLoader = servletContext.getClassLoader();
        ConfigProviderResolver providerResolver = ConfigProviderResolver.instance();
        ConfigBuilder configBuilder = providerResolver.getBuilder();
        configBuilder.forClassLoader(classLoader);
        // 初始化默认配置来源
        configBuilder.addDefaultSources();
        // 通过发现获取配置来源
        configBuilder.addDiscoveredSources();
        ServletConfigSource servletConfigSource = new ServletConfigSource(servletContext);
        // api 注册配置来源
        configBuilder.withSources(servletConfigSource);
        // 注册默认转换器
        configBuilder.addDiscoveredConverters();
        Config defaultConfig = configBuilder.build();
        providerResolver.registerConfig(defaultConfig, classLoader);
        servletContext.setAttribute("config", defaultConfig);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
