package myframework.servicefactory.definition.impl;

import myframework.servicefactory.definition.Definition;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Robin
 * @date 2019/11/29 -16:22
 */
public class ServiceDefinition implements Definition
{
    private final Set<Method> methods;

    private final Class serviceClass;

    private final String serviceName;

    public ServiceDefinition(Set<Method> methods, Class serviceClass, String serviceName)
    {
        this.methods = methods;
        this.serviceClass = serviceClass;
        this.serviceName = serviceName;
    }

    @Override
    public Set<Method> getMethods()
    {
        return methods;
    }

    @Override
    public Class getServiceClass()
    {
        return serviceClass;
    }

    @Override
    public String getServiceName()
    {
        return serviceName;
    }

    /**
     * 某类的所有方法映射
     */
    private final Map<Class, Set<Method>> serviceDefinitionMap = new ConcurrentHashMap<>();

    /**
     * servicename和class的映射
     */
    private final Map<String, Class> serviceClassMap = new ConcurrentHashMap<>();


}
