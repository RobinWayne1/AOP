package myframework.core.definition.impl;

import myframework.core.definition.Definition;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Robin
 * @date 2019/11/29 -16:22
 */
public class BeanDefinition implements Definition
{
    /**
     * 供AOP使用
     */
//    private final List<Method> methods;

    private final Class beanClass;

    private final String beanName;

    public BeanDefinition( Class beanClass, String beanName)
    {

        this.beanClass = beanClass;
        this.beanName = beanName;
    }

//    @Override
//    public List<Method> getMethods()
//    {
//        return methods;
//    }

    @Override
    public Class getBeanClass()
    {
        return beanClass;
    }

    @Override
    public String getBeanName()
    {
        return beanName;
    }

//    /**
//     * 某类的所有方法映射
//     */
//    private final Map<Class, Set<Method>> serviceDefinitionMap = new ConcurrentHashMap<>();
//
//    /**
//     * servicename和class的映射
//     */
//    private final Map<String, Class> serviceClassMap = new ConcurrentHashMap<>();


}
