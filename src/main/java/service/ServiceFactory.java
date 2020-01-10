package service;

import myframework.core.aware.ApplicationContextAware;
import myframework.core.aware.BeanFactoryAware;
import myframework.core.factory.BeanFactory;
import myframework.core.factory.WebApplicationContext;

/**
 * @author Robin
 * @date 2020/1/10 -15:46
 */
public class ServiceFactory implements BeanFactoryAware
{
    private static BeanFactory factory;

    /**
     * 将beanFactory传给bean
     *
     * @param beanFactory
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory)
    {
        ServiceFactory.factory=beanFactory;
    }

    public static UserInfoService getUserInfoService()
    {
        return (UserInfoService)factory.getBean("userinfoserviceimpl");
    }
    public static UserService getUserService()
    {
        return (UserService)factory.getBean("userserviceimpl");
    }
}
