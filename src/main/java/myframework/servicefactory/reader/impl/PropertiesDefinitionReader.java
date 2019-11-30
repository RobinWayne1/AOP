package myframework.servicefactory.reader.impl;

import myframework.servicefactory.context.BeanFactory;
import myframework.servicefactory.definition.Definition;
import myframework.servicefactory.definition.impl.BeanDefinition;
import myframework.servicefactory.reader.DefinitionReader;
import myframework.util.ConfigUtil;
import myframework.util.FileUtil;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Robin
 * @date 2019/11/29 -17:56
 */
public class PropertiesDefinitionReader  implements DefinitionReader
{
//    private final String PROPERTIES_PATH;
//
//    public PropertiesDefinitionReader(String path)
//    {
//        this.PROPERTIES_PATH=path;
//    }

    /**
     * 获得配置中的sevice类,并加载进JVM
     */
    @Override
    public void loadServiceDefinition(BeanFactory beanFactory)
    {
        String[] servicePackages = ConfigUtil.getServicePackage();
        for (String p : servicePackages)
        {
            Set<Class<?>> classes = FileUtil.getClasses(p);
            for (Class cl : classes)
            {
                String serviceName = cl.getName().toLowerCase();
                Definition definition = new BeanDefinition(new CopyOnWriteArraySet<>(Arrays.asList(cl.getDeclaredMethods())), cl, serviceName);
                beanFactory.getBeanDefinitionMap().put(serviceName, definition);
            }
        }
    }
}
