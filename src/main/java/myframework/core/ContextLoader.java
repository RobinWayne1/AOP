package myframework.core;

import myframework.core.factory.WebApplicationContext;
import myframework.core.factory.impl.PropertiesApplicationContext;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author Robin
 * @date 2019/11/29 -17:15
 */
public class ContextLoader
{

    private WebApplicationContext context;

    public void initWebApplicationContext(ServletContextEvent sce)
    {
        ServletContext sc=sce.getServletContext();
        if (this.context == null)
        {
            WebApplicationContext context = new PropertiesApplicationContext();
            sce.getServletContext().setAttribute(WebApplicationContext.ROOT_CONTEXT_NAME,context);
            this.context=context;
        }
        //暂时不支持扩展
        else if(this.context instanceof PropertiesApplicationContext&&sc.getAttribute(WebApplicationContext.ROOT_CONTEXT_NAME)==null)
        {
            sce.getServletContext().setAttribute(WebApplicationContext.ROOT_CONTEXT_NAME,this.context);
        }
        else if(!(this.context instanceof PropertiesApplicationContext))
        {
            throw new RuntimeException("factory initialized failed!");
        }
    }
}
