package myframework.aop.advisor.factory;

import myframework.aop.anntations.After;
import myframework.aop.anntations.Around;
import myframework.aop.anntations.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * spring里此类有找切面方法注解的方法,而我可以直接使用aspectmetadata找
 *
 * @author Robin
 * @date 2019/12/3 -23:42
 */
public abstract class AbstractAdvisorFactory implements AspectAdvisorFactory
{
    private final static Class[] ASPECTJ_ANNOTATION_CLASSES = {Around.class, Before.class, After.class};

    /**
     * 过滤有AOP注解的方法
     * @param method
     * @return
     */
    protected static Annotation findAspectJAnnotationOnMethod(Method method)
    {
        for (Class aac : ASPECTJ_ANNOTATION_CLASSES)
        {
            Annotation annotation = method.getAnnotation(aac);

            if (annotation != null)
            {
                return annotation;
            }
        }
        return null;
    }
}