package myframework.core.processor;

/**
 *
 * 后处理器,在实例化bean的前后进行一些处理
 * @author Robin
 * @date 2019/12/1 -16:59
 */
public interface BeanPostProcessor
{
    /**
     * 前置后处理器
     * @param bean
     * @param beanName
     * @return
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) //throws BeansException
    {
        return bean;
    }

    /**
     * 后置后处理器
     * @param bean
     * @param beanName
     * @return
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) //throws BeansException
    {
        return bean;
    }
}
