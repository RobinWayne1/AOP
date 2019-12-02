package myframework.core.reader.impl;

import myframework.aop.framework.AnnotationAwareAutoProxyCreator;
import myframework.core.definition.Definition;
import myframework.core.definition.impl.AspectBeanDefinition;
import myframework.core.definition.impl.BeanDefinition;
import myframework.core.reader.DefinitionReader;
import myframework.util.ConfigUtil;
import myframework.util.FileUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 切面配置的读取由此类完成
 *
 * @author Robin
 * @date 2019/11/30 -17:18
 */
public class AspectConfigDefinitionReader implements DefinitionReader
{
    /**
     * 获得配置中的config
     *
     * @param map
     */
    @Override
    public void loadBeanDefinition(Map<String, Definition> map)
    {
        String[] aspects = ConfigUtil.getProxyClass();
        for (String aspect : aspects)
        {
            Set<Class<?>> classSet = FileUtil.getClasses(aspect);
            for (Class cl : classSet)
            {
                Method[] methods = cl.getDeclaredMethods();
                List<Method> methodList=new CopyOnWriteArrayList<>(methods);
                String clName=cl.getName();

                String beanName =clName.substring(clName.lastIndexOf("."),clName.length()).toLowerCase();
                Map<Method, Annotation[]> adviceMethodInfoMap=new ConcurrentHashMap<>(16);
                Annotation[]annotations;
                for(Method method:methods)
                {
                    annotations=method.getAnnotations();
                    adviceMethodInfoMap.put(method,annotations);
                }
                Definition definition=new AspectBeanDefinition(methodList,cl,beanName,adviceMethodInfoMap);
                map.put(beanName,definition);
            }
        }
        if(aspects.length!=0)
        {
            Definition definition=new BeanDefinition(null, AnnotationAwareAutoProxyCreator.class,"annotationAwareAutoProxyCreator");
            map.put("annotationAwareAutoProxyCreator",definition);
        }
    }
}
