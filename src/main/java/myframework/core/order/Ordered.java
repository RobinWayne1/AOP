package myframework.core.order;

/**
 * @author Robin
 * @date 2019/12/14 -11:11
 */
public interface Ordered
{
    /**
     * 最高优先级
     */
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    /**
     * 最低优先级
     */
    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;


    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    int getOrder();

}
