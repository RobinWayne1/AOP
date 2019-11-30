package myframework.servicefactory.context.impl;


import myframework.servicefactory.context.Context;
import myframework.servicefactory.context.BeanFactory;

/**
 * @author Robin
 * @date 2019/11/29 -12:33
 */
public class WebApplicationContext implements Context
{


    private final BeanFactory serviceFactory ;

//    public WebApplicationContext(String configPath)
//    {
//        serviceFactory=new DefaultServiceFactory(configPath);
//    }

    public WebApplicationContext()
    {
        serviceFactory=new DefaultBeanFactory();
        serviceFactory.parseAndCreateInstance();
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
        return serviceFactory.getBean(beanName);
    }
}
