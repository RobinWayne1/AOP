package myframework.util;

import myframework.aop.anntations.Order;
import myframework.aop.pointcut.ClassFilter;

import java.lang.annotation.Annotation;

/**
 * @author Robin
 * @date 2020/1/4 -22:55
 */
public class OrderUtils
{
    public static int getOrder(Class cl,int defaultOrder)
    {
        Order ann = AnnotationUtils.findAnnotation(cl, Order.class);
        if (ann != null)
        {
            return (ann).value();
        }
        return defaultOrder;
    }
}
