package org.geektimes.projects.user.web.listener;

import org.geektimes.context.ComponentContext;
import org.geektimes.projects.user.sql.DBConnectionManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * TestListener
 *
 * @author qrXun on 2021/3/9
 */
@Deprecated
public class TestListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ComponentContext componentContext = ComponentContext.getInstance();
        DBConnectionManager dbConnectionManager = componentContext.getComponent("bean/DBConnectionManager");
        dbConnectionManager.createUserChart();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
