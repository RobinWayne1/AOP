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
     * 本来返回类型是int改成了Integer,防止InstantiationModelAwarePointcutAdvisorImpl的getOrder()
     * 发生错误,即如果返回null则要进行注解抓取order,而我希望advisor使用注解抓取,而ExposeMethodInterceptor不用抓取
     */
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
