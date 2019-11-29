package myframework.servicefactory.reader;

import myframework.servicefactory.context.ServiceFactory;
import myframework.servicefactory.definition.impl.ServiceDefinition;

/**
 * @author Robin
 * @date 2019/11/29 -18:02
 */
public interface DefinitionReader
{
    /**
     * 获得配置中的service类,并加载进JVM
     *
     * @param serviceFactory
     */
    void loadServiceDefinition(ServiceFactory serviceFactory);

}
