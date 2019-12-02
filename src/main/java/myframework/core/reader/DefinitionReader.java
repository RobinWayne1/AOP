package myframework.core.reader;

import myframework.core.definition.Definition;

import java.util.Map;

/**
 * @author Robin
 * @date 2019/11/29 -18:02
 */
public interface DefinitionReader
{
    /**
     * 获得配置中的config
     * @param map
     */
    void loadBeanDefinition(Map<String, Definition> map);

}
