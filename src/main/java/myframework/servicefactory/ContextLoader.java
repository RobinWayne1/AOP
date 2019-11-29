package myframework.servicefactory;

import myframework.servicefactory.context.Context;
import myframework.servicefactory.context.impl.WebApplicationContext;

/**
 * @author Robin
 * @date 2019/11/29 -17:15
 */
public class ContextLoader
{

    protected Context createWebApplicationContext()
    {
        Context context=new WebApplicationContext();

    }
}
