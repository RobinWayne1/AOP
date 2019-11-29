package myframework.servicefactory.context;

import myframework.servicefactory.definition.Definition;

import java.util.Map;
import java.util.Set;

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

    /**
     * 返回service信息
     * @return
     */
    Map<String, Definition> getServiceDefinitionMap();


    /**
     * 返回service名
     * @return
     */
    Set<String> getServiceName();


}
