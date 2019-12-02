package myframework.aop.factory;

/**
 * @author Robin
 * @date 2019/12/2 -22:02
 */
public interface AspectInstanceFactory
{
    /**
     * 获取切面实例,此时切面相当于普通的bean由
     * {@link myframework.core.factory.impl.DefaultBeanFactory}创建了
     * @return
     */
    Object getAspectInstance();
}
