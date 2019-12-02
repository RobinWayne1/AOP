package myframework.servicefactory.aware;

import myframework.servicefactory.factory.BeanFactory;

/**
 *
 *
 * @author Robin
 * @date 2019/12/2 -17:34
 */
public interface BeanFactoryAware extends Aware
{
    /**
     * 将beanFactory传给bean
     * @param beanFactory
     */
    void setBeanFactory(BeanFactory beanFactory);
}
