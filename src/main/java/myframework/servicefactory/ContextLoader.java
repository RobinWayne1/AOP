package myframework.servicefactory;

import myframework.servicefactory.context.Context;
import myframework.servicefactory.context.impl.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author Robin
 * @date 2019/11/29 -17:15
 */
public class ContextLoader
{

    private Context context;

    public void initWebApplicationContext(ServletContextEvent sce)
    {
        ServletContext sc=sce.getServletContext();
        if (this.context == null)
        {
            Context context = new WebApplicationContext();
            sce.getServletContext().setAttribute(Context.ROOT_CONTEXT_NAME,context);
            this.context=context;
        }
        //暂时不支持扩展
        else if(this.context instanceof WebApplicationContext&&sc.getAttribute(Context.ROOT_CONTEXT_NAME)==null)
        {
            sce.getServletContext().setAttribute(Context.ROOT_CONTEXT_NAME,this.context);
        }
        else if(!(this.context instanceof WebApplicationContext))
        {
            throw new RuntimeException("context initialized failed!");
        }
    }
}
