package myframework.core.reader.impl;

import myframework.core.definition.Definition;
import myframework.core.definition.impl.BeanDefinition;
import myframework.core.reader.DefinitionReader;
import myframework.util.ConfigUtil;
import myframework.util.FileUtil;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * 获得bean配置
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
     * 获得配置中的bean类,并加载进JVM,返回beanMap(包装器)
     */
    @Override
    public void loadBeanDefinition(Map<String, Definition> map)
    {
        String[] servicePackages = ConfigUtil.getBeanPackage();
        for (String p : servicePackages)
        {
            Set<Class<?>> classes = FileUtil.getClasses(p);
            for (Class cl : classes)
            {
                String clName=cl.getName();
                //需要修改beanname
                String beanName =clName.substring(clName.lastIndexOf("."),clName.length()).toLowerCase();
                Definition definition = new BeanDefinition( cl, beanName);
                //这里就控制了类的单例
                map.put(beanName, definition);
            }
        }
    }
}
