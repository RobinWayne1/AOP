package myframework.aop.metadata.factory;

import myframework.aop.anntations.AfterAdvice;
import myframework.aop.anntations.AroundAdvice;
import myframework.aop.anntations.BeforeAdvice;
import myframework.aop.metadata.AspectMetaData;
import myframework.core.definition.Definition;
import myframework.core.definition.impl.AspectBeanDefinition;
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
 * @author Robin
 * @date 2019/12/3 -16:47
 */
public class AspectMetaDataFactory
{
    private final static Class[] ASPECTJ_ANNOTATION_CLASSES = {AroundAdvice.class, BeforeAdvice.class, AfterAdvice.class};


    public static AspectMetaData getAspectMetaData(Class cl, String aspectName)
    {

        Method[] methods = cl.getDeclaredMethods();

        Map<Method, Annotation> adviceMethodInfoMap = new ConcurrentHashMap<>(16);

        for (Method method : methods)
        {

            for (Class aac : ASPECTJ_ANNOTATION_CLASSES)
            {
                Annotation annotation = method.getAnnotation(aac);
                //只是做了初步的过滤(没有注解的方法的过滤)
                if (annotation != null)
                {
                    adviceMethodInfoMap.put(method, annotation);
                    break;
                }
            }

        }
        return new AspectMetaData(aspectName, cl, adviceMethodInfoMap);
    }

}
