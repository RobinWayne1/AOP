package myframework.util;

import com.sun.istack.internal.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 保证了不可为空,则可以传空值进来
 *
 * @author Robin
 * @date 2020/1/4 -23:04
 */
public class AnnotationUtils
{
    public static <A extends Annotation> A findAnnotation(Method method,Class<A> annotationClass)
    {
        return  method.getAnnotation(annotationClass);
    }

    public static <A extends Annotation> A findAnnotation(Class cl,Class<A> annotationClass)
    {
        return (A)cl.getAnnotation(annotationClass);
    }

    public static Object findAnnotationValue(@Nullable String attributeName, Annotation ann)
    {
        if(attributeName==null)
        {
            attributeName="value";
        }
        if (ann != null)
        {
            try
            {
                //即得到注解的value()定义方法
                Method annotationValue = ann.annotationType().getDeclaredMethod(attributeName);
                //然后invoke这个注解定义方法得到注解目标方法上的value值
                return annotationValue.invoke(ann);
            } catch (NoSuchMethodException e)
            {

                return null;
            } catch (InvocationTargetException e)
            {
                throw new IllegalStateException(
                        "Could not obtain value for annotation attribute '" + attributeName + "' in " + ann, e);
            } catch (IllegalAccessException e)
            {
                //通常为反射方法是private所致
                return null;
            }
        }
        return null;
    }
}
