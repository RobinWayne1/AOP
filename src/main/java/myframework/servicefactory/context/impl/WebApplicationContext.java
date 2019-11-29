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

    public WebApplicationContext(DefinitionReader reader)
    {
        serviceFactory=new AbstractDefaultServiceFactory(reader.getServiceDefinition());
    }


    /**
     * 从ObjectFactory中返回指定bean
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName)
    {
        return null;
    }
}
