package org.geektimes.projects.user.web.listener;

import org.geektimes.projects.user.sql.DBConnectionManager;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class DBConnectionInitializerListener implements ServletContextListener {

    @Resource(lookup = "java:comp/env", name = "jdbc/UserPlatformDB")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("初始化 dataSource");
        System.out.println(dataSource);
        DBConnectionManager.setDataSource(dataSource);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
