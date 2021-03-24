package org.geektimes.servlet;

import org.geektimes.context.ComponentContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletComponentContextInitializedListener
 *
 * @author qrXun on 2021/3/23
 */
public class ServletComponentContextInitializedListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        ComponentContext componentContext = new ComponentContext();
        componentContext.init(context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
