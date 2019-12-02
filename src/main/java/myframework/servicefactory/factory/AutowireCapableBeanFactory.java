package myframework.servicefactory.factory;

import myframework.servicefactory.processor.BeanPostProcessor;

/**
 *
 * 虽然还没实现autowire,但是此接口是用来做创建bean的相关工作
 * @author Robin
 * @date 2019/12/2 -18:01
 */
public interface AutowireCapableBeanFactory extends BeanFactory
{

    /**
     * 给factory添加后处理器
     * @param postProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    /**
     * 创建bean实例
     */
    void createBean();

    /**
     *
     * @param existingBean
     * @param beanName
     * @return
     */
    Object initializeBean(Object existingBean, String beanName);
}
