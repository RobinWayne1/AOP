package myframework.core.order.comparator;

import myframework.aop.anntations.Order;
import myframework.core.order.Ordered;
import myframework.util.AnnotationUtils;
import myframework.util.OrderUtils;

import java.lang.reflect.Method;


/**
 * 此类仅仅是对声明@Order()的支持,只对Order进行了排序,AOP之间的偏序关系并没有在这里考虑
 * 此类在AOP排序时的作用是在AspectJPrecedenceComparator.compare()方法中对advisor排序前查看
 * advisor对应切面的order
 * @author Robin
 * @date 2020/1/5 -22:47
 */
public class AnnotationAwareOrderComparator extends OrderComparator
{
    public static final AnnotationAwareOrderComparator INSTANCE = new AnnotationAwareOrderComparator();
    @Override
    protected Integer findOrder(Object obj)
    {
        // Check for regular Ordered interface
        Integer order = super.findOrder(obj);
        if (order != null) {
            return order;
        }

        // Check for @Order and @Priority on various kinds of elements
        if (obj instanceof Class) {
            return OrderUtils.getOrder((Class<?>) obj, Ordered.LOWEST_PRECEDENCE);
        }
        else if (obj instanceof Method) {
            Order ann = AnnotationUtils.findAnnotation((Method) obj, Order.class);
            if (ann != null) {
                return ann.value();
            }
        }
        return null;
    }
}
