package myframework.core.factory;

import myframework.core.definition.Definition;

import java.util.List;
import java.util.Map;

/**
 * 提供对beanfactory的访问功能
 *
 * @author Robin
 * @date 2019/12/1 -17:36
 */
public interface ConfigurableBeanFactory extends BeanFactory
{



    /**
     * 根据类型获得bean的名字
     * @param cl
     * @return
     */
    List<String> getBeanNamesForType(Class<?>cl);

    /**
     * 返回bean信息
     *
     * @return
     */
    Map<String, Definition> getBeanDefinitionMap();

    /**
     * 返回bean的类型
     * @param beanName
     * @return
     */
    Class<?> getType(String beanName);

}
