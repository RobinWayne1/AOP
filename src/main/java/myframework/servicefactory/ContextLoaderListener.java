package myframework.servicefactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Robin
 * @date 2019/11/28 -23:49
 */
public class ContextLoaderListener extends ContextLoader implements ServletContextListener
{

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {

        sce.getServletContext();
    }
}
