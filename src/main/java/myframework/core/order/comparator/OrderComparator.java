package myframework.core.order.comparator;

import com.sun.istack.internal.Nullable;
import myframework.core.order.Ordered;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Robin
 * @date 2020/1/5 -22:47
 */
public class OrderComparator implements Comparator<Object>
{

    /**
     * Shared default instance of {@code OrderComparator}.
     */
    public static final OrderComparator INSTANCE = new OrderComparator();


    /**
     * Build an adapted order comparator with the given source provider.
     * @param sourceProvider the order source provider to use
     * @return the adapted comparator
     * @since 4.1
     */
    public Comparator<Object> withSourceProvider(OrderSourceProvider sourceProvider) {
        return (o1, o2) -> doCompare(o1, o2, sourceProvider);
    }

    @Override
    public int compare(@Nullable Object o1, @Nullable Object o2) {
        return doCompare(o1, o2, null);
    }

    private int doCompare(@Nullable Object o1, @Nullable Object o2, @Nullable OrderSourceProvider sourceProvider) {
        boolean p1 = (o1 instanceof Ordered);
        boolean p2 = (o2 instanceof Ordered);
        if (p1 && !p2) {
            return -1;
        }
        else if (p2 && !p1) {
            return 1;
        }
        /**
         * getOrder()方法与spring不同
         */
        int i1 = getOrder(o1);
        int i2 = getOrder(o2);
        return Integer.compare(i1, i2);
    }



    /**
     * Determine the order value for the given object.
     * <p>The default implementation checks against the {@link Ordered} interface
     * through delegating to {@link #findOrder}. Can be overridden in subclasses.
     * @param obj the object to check
     * @return the order value, or {@code Ordered.LOWEST_PRECEDENCE} as fallback
     */
    protected int getOrder(@Nullable Object obj) {
        if (obj != null) {
            Integer order = findOrder(obj);
            if (order != null) {
                return order;
            }
        }
        return Ordered.LOWEST_PRECEDENCE;
    }



    /**
     * Find an order value indicated by the given object.
     * <p>The default implementation checks against the {@link Ordered} interface.
     * Can be overridden in subclasses.
     * @param obj the object to check
     * @return the order value, or {@code null} if none found
     */
    @Nullable
    protected Integer findOrder(Object obj) {
        return (obj instanceof Ordered ? ((Ordered) obj).getOrder() : null);
    }



    /**
     * Sort the given List with a default OrderComparator.
     * <p>Optimized to skip sorting for lists with size 0 or 1,
     * in order to avoid unnecessary array extraction.
     * @param list the List to sort
     * @see java.util.List#sort(java.util.Comparator)
     */
    public static void sort(List<?> list) {
        if (list.size() > 1) {
            list.sort(INSTANCE);
        }
    }

    /**
     * Sort the given array with a default OrderComparator.
     * <p>Optimized to skip sorting for lists with size 0 or 1,
     * in order to avoid unnecessary array extraction.
     * @param array the array to sort
     * @see java.util.Arrays#sort(Object[], java.util.Comparator)
     */
    public static void sort(Object[] array) {
        if (array.length > 1) {
            Arrays.sort(array, INSTANCE);
        }
    }

    /**
     * Sort the given array or List with a default OrderComparator,
     * if necessary. Simply skips sorting when given any other value.
     * <p>Optimized to skip sorting for lists with size 0 or 1,
     * in order to avoid unnecessary array extraction.
     * @param value the array or List to sort
     * @see java.util.Arrays#sort(Object[], java.util.Comparator)
     */
    public static void sortIfNecessary(Object value) {
        if (value instanceof Object[]) {
            sort((Object[]) value);
        }
        else if (value instanceof List) {
            sort((List<?>) value);
        }
    }


    /**
     * Strategy interface to provide an order source for a given object.
     * @since 4.1
     */
    @FunctionalInterface
    public interface OrderSourceProvider {

        /**
         * Return an order source for the specified object, i.e. an object that
         * should be checked for an order value as a replacement to the given object.
         * <p>Can also be an array of order source objects.
         * <p>If the returned object does not indicate any order, the comparator
         * will fall back to checking the original object.
         * @param obj the object to find an order source for
         * @return the order source for that object, or {@code null} if none found
         */
        @Nullable
        Object getOrderSource(Object obj);
    }

}
