package myframework.servicefactory;

import myframework.servicefactory.context.Context;
import myframework.servicefactory.context.impl.WebApplicationContext;

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
        Context ctx=new WebApplicationContext();
        sce.getServletContext().setAttribute(Context.ROOT_CONTEXT_NAME,ctx);
    }
}
