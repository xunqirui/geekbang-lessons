package org.geektimes.servlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * ServletComponentContextInitialized
 *
 * @author qrXun on 2021/3/23
 */
public class ServletComponentContextInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        ctx.addListener(new ServletComponentContextInitializedListener());
    }
}
