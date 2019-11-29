package myframework.servicefactory.context.impl;

import myframework.servicefactory.context.ServiceFactory;
import myframework.servicefactory.definition.Definition;
import myframework.servicefactory.definition.impl.ServiceDefinition;
import myframework.servicefactory.reader.DefinitionReader;
import myframework.servicefactory.reader.impl.PropertiesDefinitionReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Robin
 * @date 2019/11/29 -17:01
 */
public class DefaultServiceFactory implements ServiceFactory
{
    /**
     * service映射
     */
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    private final Map<String, Definition> serviceDefinitionMap = new ConcurrentHashMap<>();

    private final DefinitionReader reader;



    @Override
    public Map<String, Definition> getServiceDefinitionMap()
    {
        return serviceDefinitionMap;
    }
//
//    public DefaultServiceFactory(String configPath)
//    {
//        reader = new PropertiesDefinitionReader(configPath);
//
//    }

    public DefaultServiceFactory()
    {
        reader = new PropertiesDefinitionReader();

    }

    /**
     * 刷新容器
     */
    public void parseAndCreateInstance()
    {
        //解析Config
        reader.loadServiceDefinition(this);
        //创建bean实例
        instantiateClass();
    }

    @Override
    public Set<String> getServiceName()
    {
        return this.serviceDefinitionMap.keySet();
    }

    /**
     * 实例化service
     */
    private synchronized void instantiateClass()
    {
        Set<String> serviceNames = getServiceName();
        try
        {
            for (String serviceName : serviceNames)
            {
                Class cl = this.serviceDefinitionMap.get(serviceName).getServiceClass();
                if (cl.isInterface())
                {
                    throw new RuntimeException("Specified class is an interface");
                }
                Object serviceInstance = cl.newInstance();
                serviceMap.put(serviceName, serviceInstance);

            }
        } catch (InstantiationException e)
        {
            throw new RuntimeException("Is there an abstract class?");
        } catch (IllegalAccessException e)
        {
            throw new RuntimeException("Is the constructor accessible?");
        }
    }


    private Object getSingleton(String serviceName)
    {
        return serviceMap.get(serviceName);

    }

    /**
     * 返回指定bean
     *
     * @param serviceName
     * @return
     */
    @Override
    public Object getBean(String serviceName)
    {
        return getSingleton(serviceName);
    }
}
