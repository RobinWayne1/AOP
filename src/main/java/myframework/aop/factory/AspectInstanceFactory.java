package myframework.aop.factory;


import myframework.core.order.Ordered;

/**
 * @author Robin
 * @date 2019/12/2 -22:02
 */
public interface AspectInstanceFactory extends Ordered
{
    /**
     * 获取切面实例,此时切面相当于普通的bean由
     * {@link myframework.core.factory.impl.DefaultBeanFactory}创建了
     * @return
     */
    Object getAspectInstance();


}
