package myframework.aop.factory.impl;

import myframework.aop.factory.AspectInstanceFactory;
import myframework.aop.factory.MetaDataAwareAspectInstanceFactory;


import myframework.aop.metadata.AspectMetadata;
import myframework.core.factory.BeanFactory;
import myframework.core.definition.Definition;
import myframework.core.factory.ConfigurableInstantiationCapableBeanFactory;
import myframework.core.factory.impl.DefaultBeanFactory;
import myframework.core.order.Ordered;
import myframework.util.OrderUtils;

import java.util.Map;
import java.util.Set;

/**
 * aspectBean的factory,主要作用是用来获得aspect实例,由于spring的BeanFactoryAspectInstanceFactory
 * 是单例bean时使用的factory,所以这里改个名字
 *
 * @author Robin
 * @date 2019/11/30 -17:51
 */
//后处理器执行时开始build,作用是可以包装defaultbeanfactory和得到aspectBean
public class NonLazyInitAspectInstanceFactory implements MetaDataAwareAspectInstanceFactory
{

    private final String aspectName;

    private final ConfigurableInstantiationCapableBeanFactory beanFactory;

    private final AspectMetadata aspectMetadata;

    public NonLazyInitAspectInstanceFactory(String aspectName, ConfigurableInstantiationCapableBeanFactory beanFactory,Class<?> beanType)
    {
        this.aspectName = aspectName;
        this.beanFactory = beanFactory;
        this.aspectMetadata= new AspectMetadata(aspectName,beanType);
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

    /**
     * 返回切面元信息
     *
     * @return
     */
    @Override
    public AspectMetadata getAspectMetaData()
    {
        return this.aspectMetadata;
    }


    @Override
    public int getOrder() {
        Class<?> type = this.beanFactory.getType(this.aspectName);
        if (type != null) {
            //判断是否是Ordered类的子类
            if (Ordered.class.isAssignableFrom(type)) {
                return ((Ordered) this.beanFactory.getBean(this.aspectName)).getOrder();
            }
            return OrderUtils.getOrder(type, Ordered.LOWEST_PRECEDENCE);
        }
        return Ordered.LOWEST_PRECEDENCE;
    }
}
