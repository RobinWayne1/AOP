package myframework.servicefactory.factory;



/**
 *
 * 有时间需要改写合并context和beanfactory
 * @author Robin
 * @date 2019/11/29 -12:32
 */
public interface Context
{


    /**
     * 从ObjectFactory中返回指定bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
