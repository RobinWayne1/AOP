package myframework.servicefactory.reader.impl;

import myframework.servicefactory.context.impl.AbstractDefaultServiceFactory;
import myframework.servicefactory.definition.Definition;
import myframework.servicefactory.definition.impl.ServiceDefinition;
import myframework.util.ConfigUtil;
import myframework.util.FileUtil;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Robin
 * @date 2019/11/29 -17:56
 */
public class PropertiesDefinitionReader extends AbstractDefaultServiceFactory
{

    /**
     * 获得配置中的sevice类,并加载进JVM
     */
    @Override
    public void loadServiceDefinition()
    {

        String[] servicePackages = ConfigUtil.getServicePackage();
        for (String p : servicePackages)
        {
            Set<Class<?>> classes = FileUtil.getClasses(p);
            for (Class cl : classes)
            {

                String serviceName = cl.getName().toLowerCase();
                Definition definition = new ServiceDefinition(new CopyOnWriteArraySet<>(Arrays.asList(cl.getDeclaredMethods())), cl, serviceName);
                getServiceDefinitionMap().put(serviceName, definition);
            }
        }
    }
}
