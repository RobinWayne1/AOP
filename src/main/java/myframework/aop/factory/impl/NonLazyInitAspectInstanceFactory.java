package myframework.aop.factory.impl;

import myframework.aop.factory.AspectInstanceFactory;
import myframework.core.factory.BeanFactory;
import myframework.core.definition.Definition;
import myframework.core.factory.ConfigurableInstantiationCapableBeanFactory;
import myframework.core.factory.impl.DefaultBeanFactory;

import java.util.Map;
import java.util.Set;

/**
 * aspectBean的factory,主要作用是用来获得aspect实例,由于spring的BeanFactoryAspectInstanceFactory
 * 是单例bean时使用的factory,所以这里改个名字
 *
 * 此处暂时没有什么有用的功能,可以留给以后扩展
 * @author Robin
 * @date 2019/11/30 -17:51
 */
//后处理器执行时开始build,作用是可以包装defaultbeanfactory和得到aspectBean
public class NonLazyInitAspectInstanceFactory implements AspectInstanceFactory
{

    private final String aspectName;

    private final ConfigurableInstantiationCapableBeanFactory beanFactory;

    public NonLazyInitAspectInstanceFactory(String aspectName, ConfigurableInstantiationCapableBeanFactory beanFactory)
    {
        this.aspectName = aspectName;
        this.beanFactory = beanFactory;
    }

    /**
     * 获取切面实例,此时切面相当于普通的bean由
     * {@link DefaultBeanFactory}创建了
     *
     * @return
     */
    @Override
    public Object getAspectInstance()
    {
        return this.beanFactory.getBean(this.aspectName);
    }
}
