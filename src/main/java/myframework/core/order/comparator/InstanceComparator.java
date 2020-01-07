package myframework.core.order.comparator;

import com.sun.istack.internal.Nullable;

import java.util.Comparator;

/**
 * @author Robin
 * @date 2020/1/7 -16:03
 */
public class InstanceComparator<T> implements Comparator<T>
{
    /**
     * 即定义排列的顺序
     */
    private final Class<?>[] instanceOrder;



    public InstanceComparator(Class<?>... instanceOrder) {
        this.instanceOrder = instanceOrder;
    }


    @Override
    public int compare(T o1, T o2) {
        int i1 = getOrder(o1);
        int i2 = getOrder(o2);
        return Integer.compare(i1,i2);
    }

    /**
     * AOP的初次排序逻辑在这里,根据instanceOrder的顺序,返回在instanceOrder的下标,且这里的instanceOrder是注解类
     *
     * 这里我将他改成了最终排序使用的逻辑
     * @param object
     * @return
     */
    private int getOrder(@Nullable T object) {
        if (object != null) {
            for (int i = 0; i < this.instanceOrder.length; i++) {
                if (this.instanceOrder[i].isInstance(object)) {
                    return i;
                }
            }
        }
        return this.instanceOrder.length;
    }

}
