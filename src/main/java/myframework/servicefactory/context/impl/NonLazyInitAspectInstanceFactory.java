package myframework.servicefactory.context.impl;

import myframework.servicefactory.context.BeanFactory;
import myframework.servicefactory.definition.Definition;

import java.util.Map;
import java.util.Set;

/**
 * @author Robin
 * @date 2019/11/30 -17:51
 */
public class NonLazyInitAspectInstanceFactory implements BeanFactory
{
    /**
     * 返回指定bean
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName)
    {
        return null;
    }

    /**
     * 返回Bean信息
     *
     * @return
     */
    @Override
    public Map<String, Definition> getBeanDefinitionMap()
    {
        return null;
    }

    /**
     * 返回Bean名
     *
     * @return
     */
    @Override
    public Set<String> getBeanName()
    {
        return null;
    }

    /**
     * 刷新容器
     */
    @Override
    public void parseAndCreateInstance()
    {

    }
}
