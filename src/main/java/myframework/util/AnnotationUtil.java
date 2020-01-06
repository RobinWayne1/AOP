package myframework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 保证了不可为空,则可以传空值进来
 *
 * @author Robin
 * @date 2020/1/4 -23:04
 */
public class AnnotationUtil
{
    public static Object findAnnotationValue(String attributeName, Annotation ann)
    {
        if (attributeName != null && ann != null)
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
