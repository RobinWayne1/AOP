package myframework.core.factory;

import java.util.Set;

/**
 * @author Robin
 * @date 2019/11/29 -12:37
 */
public interface BeanFactory
{


    /**
     * 返回指定bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);


    /**
     * 返回bean信息
     * @return
     */
    Set<String> getBeanName();

//    /**
//     * 刷新容器
//     */
//    void createInstance();
}
