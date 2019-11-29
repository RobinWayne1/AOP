package myframework.servicefactory.context.impl;


import myframework.servicefactory.context.Context;
import myframework.servicefactory.context.ServiceFactory;
import myframework.servicefactory.reader.DefinitionReader;

/**
 * @author Robin
 * @date 2019/11/29 -12:33
 */
public class WebApplicationContext implements Context
{


    private final ServiceFactory serviceFactory ;

//    public WebApplicationContext(String configPath)
//    {
//        serviceFactory=new DefaultServiceFactory(configPath);
//    }

    public WebApplicationContext()
    {
        serviceFactory=new DefaultServiceFactory();
    }

    /**
     * 从ObjectFactory中返回指定bean
     *
     * @param serviceName
     * @return
     */
    @Override
    public Object getService(String serviceName)
    {
        return serviceFactory.getBean(serviceName);
    }
}
