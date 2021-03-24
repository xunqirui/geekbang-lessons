package org.geektimes.projects.user.web.listener;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.geektimes.context.ComponentContext;
import org.geektimes.projects.user.management.UserInfo;
import org.geektimes.projects.user.management.UserInfoManage;
import org.geektimes.projects.user.sql.DBConnectionManager;

import javax.management.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;

/**
 * TestListener
 *
 * @author qrXun on 2021/3/9
 */
@Deprecated
public class TestListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        ComponentContext componentContext = ComponentContext.getInstance();
//        DBConnectionManager dbConnectionManager = componentContext.getComponent("bean/DBConnectionManager");
//        dbConnectionManager.createUserChart();
        // 注册 MBean
        try {
            registerMBean();
        } catch (Exception e) {
            System.out.println("注册 MBean 失败");
            e.printStackTrace();
        }

        // 获取配置
//        ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
//        Config config = configProviderResolver.getConfig();
//        String applicationName = config.getValue("application_name", String.class);
//        System.out.println("当前 applicationName 为 " + applicationName);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    /**
     * 注册 UserInfoManageMBean
     */
    private void registerMBean() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("org.geektimes.projects.user.management:type=UserInfo");
        mBeanServer.registerMBean(createUserInfoMBean(new UserInfo()), objectName);
    }

    private Object createUserInfoMBean(UserInfo userInfo) {
        return new UserInfoManage(userInfo);
    }
}
