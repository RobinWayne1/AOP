package myframework.aop.metadata.factory;

import myframework.aop.anntations.After;
import myframework.aop.anntations.Around;
import myframework.aop.anntations.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Robin
 * @date 2019/12/3 -16:47
 */
//public class AspectMetaDataFactory
//{
//    private final static Class[] ASPECTJ_ANNOTATION_CLASSES = {Around.class, Before.class, After.class};
//
//
//    //在此注意一下,私有内部类在其他类中是连声明都无法声明,而内部类私有方法可以被声明,但无法创建内部类对象
//    /**
//     *
//     * @param cl
//     * @param aspectName
//     * @return
//     */
//    public static AspectMetadata getAspectMetaData(Class cl, String aspectName)
//    {
//
//        Method[] methods = cl.getDeclaredMethods();
//
//        Map<Method, Annotation> adviceMethodInfoMap = new ConcurrentHashMap<>(16);
//
//        for (Method method : methods)
//        {
//
//            for (Class aac : ASPECTJ_ANNOTATION_CLASSES)
//            {
//                Annotation annotation = method.getAnnotation(aac);
//
//                if (annotation != null)
//                {
//                    adviceMethodInfoMap.put(method, annotation);
//                    break;
//                }
//            }
//
//        }
//        return new AspectMetadata(aspectName, cl, adviceMethodInfoMap);
//    }
//
//}
