package myframework.servicefactory.reader;

import myframework.servicefactory.context.BeanFactory;

/**
 * @author Robin
 * @date 2019/11/29 -18:02
 */
public interface DefinitionReader
{
    /**
     * 获得配置中的config
     *
     * @param beanFactory
     */
    void loadServiceDefinition(BeanFactory beanFactory);

}
