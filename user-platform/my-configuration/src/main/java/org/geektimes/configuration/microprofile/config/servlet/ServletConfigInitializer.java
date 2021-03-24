package org.geektimes.configuration.microprofile.config.servlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * ServletConfigInitialized
 *
 * @author qrXun on 2021/3/23
 */
public class ServletConfigInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        ctx.addListener(new ServletContextConfigInitializedListener());
    }
}
