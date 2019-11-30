package myframework.servicefactory.context.impl;

import myframework.exception.ServiceInstantiateException;
import myframework.servicefactory.context.BeanFactory;
import myframework.servicefactory.definition.Definition;
import myframework.servicefactory.reader.DefinitionReader;
import myframework.servicefactory.reader.impl.PropertiesDefinitionReader;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Robin
 * @date 2019/11/29 -17:01
 */
public class DefaultBeanFactory implements BeanFactory
{
    /**
     * service映射
     */
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    private final Map<String, Definition> serviceDefinitionMap = new ConcurrentHashMap<>();

    private final DefinitionReader reader;


    @Override
    public Map<String, Definition> getBeanDefinitionMap()
    {
        return serviceDefinitionMap;
    }
//
//    public DefaultServiceFactory(String configPath)
//    {
//        reader = new PropertiesDefinitionReader(configPath);
//
//    }

    public DefaultBeanFactory()
    {
        reader = new PropertiesDefinitionReader();

    }

    /**
     * 刷新容器
     */
    @Override
    public void parseAndCreateInstance()
    {
        //解析Config
        reader.loadServiceDefinition(this);
        //创建bean实例
        instantiateClass();
    }

    @Override
    public Set<String> getBeanName()
    {
        return this.serviceDefinitionMap.keySet();
    }

    /**
     * 实例化service
     */
    private synchronized void instantiateClass()
    {
        Set<String> serviceNames = getBeanName();

        for (String serviceName : serviceNames)
        {
            Class cl = this.serviceDefinitionMap.get(serviceName).getBeanClass();
            try
            {
                if (cl.isInterface())
                {
                    throw new ServiceInstantiateException(cl, "Specified class is an interface");
                }
                Object serviceInstance = cl.newInstance();
                serviceMap.put(serviceName, serviceInstance);

            } catch (InstantiationException e)
            {
                throw new ServiceInstantiateException(cl, "Is there an abstract class?");
            } catch (IllegalAccessException e)
            {
                throw new ServiceInstantiateException(cl, "Is the constructor accessible?Constructor with parameters is not supported at present");
            }
        }
    }


    private Object getSingleton(String serviceName)
    {
        return serviceMap.get(serviceName);

    }

    /**
     * 返回指定bean
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName)
    {
        return getSingleton(beanName);
    }
}
