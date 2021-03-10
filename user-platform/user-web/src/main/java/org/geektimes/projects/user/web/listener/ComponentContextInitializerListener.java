package org.geektimes.projects.user.web.listener;

import org.geektimes.context.ComponentContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//@WebListener
public class ComponentContextInitializerListener implements ServletContextListener {

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
