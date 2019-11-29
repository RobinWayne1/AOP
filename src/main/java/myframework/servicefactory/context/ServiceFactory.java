package myframework.servicefactory.context;

/**
 * @author Robin
 * @date 2019/11/29 -12:37
 */
public interface ServiceFactory
{
    /**
     * 返回指定bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);


}
