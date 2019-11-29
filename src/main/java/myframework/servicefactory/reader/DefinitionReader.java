package myframework.servicefactory.reader;

import myframework.servicefactory.definition.impl.ServiceDefinition;

/**
 * @author Robin
 * @date 2019/11/29 -18:02
 */
public interface DefinitionReader
{
    /**
     * 获得配置中的sevice类,并加载进JVM
     */
    void loadServiceDefinition();

//    /**
//     * 获得definition
//     * @return ServiceDefinition
//     */
//    ServiceDefinition getServiceDefinition();
}
