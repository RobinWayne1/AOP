package myframework.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * webApplicationContext的创建入口,tomcat初始化servletContext对象时创建
 *
 * @author Robin
 * @date 2019/11/28 -23:49
 */
public class ContextLoaderListener extends ContextLoader implements ServletContextListener
{

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        initWebApplicationContext(sce);

    }
}
