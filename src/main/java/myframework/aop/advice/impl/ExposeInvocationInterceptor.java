package myframework.aop.advice.impl;

import myframework.aop.advice.MethodInterceptor;
import myframework.aop.framework.proxy.intercept.MethodInvocation;
import myframework.core.order.Ordered;
import myframework.exception.AopInvocationException;

/**
 * 揭露调用拦截器
 * <p>
 * 用于存放线程封闭的MethodInvocation,这样可以从MethodInvocation中得到attr信息,
 * 记得一定要在finally set(null),否则会造成ThreadLocalOOM
 *
 * @author Robin
 * @date 2019/12/7 -16:59
 */
public class ExposeInvocationInterceptor implements MethodInterceptor, Ordered
{
    public static final ExposeInvocationInterceptor INSATNCE = new ExposeInvocationInterceptor();

    //控制单例
    private ExposeInvocationInterceptor(){}
    /**
     * 注意这里的ThreadLocal常量,ThreadLocal只是一个对象,而真正存放需要存放的对象的是在ThreadLocalMap中,是
     * 线程独有的.所以ExposeInvocationInterceptor的ThreadLocal是所有线程共享的,而它的set()操作的ThreadLocalMap
     * 则是线程独有的.
     */

    /**
     * 想想有没有办法替代ThreadLocal,
     * 1.最简单的就是直接把MethodInvocation传进AbstractAspectJAdvice,显然,advice也只是算是拦截器链的一份子,
     * 不应该和调用拦截器链的MethodInvocation耦合
     * 2.直接设置一个static的mi,很方便使用,而由于这是类变量,这就直接把本来要设计成线程封闭mi直接发布了出去,
     * 如果有同时两个线程使用mi就必须要加锁
     * 3.所以,人手一支笔,ThreadLocal是最好的选择(回去看看JCP)
     * 即当所有线程都对某资源有需要,但是此资源又没有共享的必要性的时候,使用ThreadLocal.
     *
     * 为何不直接在invokeAdviceMethod()里传一个mi上来???
     * 答案看调用者
     */
    private static final ThreadLocal<MethodInvocation> INVOCATION_THREAD_LOCAL = new ThreadLocal<>();

    public static MethodInvocation currentInvocation()
    {
        MethodInvocation mi=INVOCATION_THREAD_LOCAL.get();
        if (mi==null)
        {
            throw new AopInvocationException(" Check if the ExposeInvocationInterceptor is upfront in the interceptor chain");
        }
        return mi;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable
    {
        MethodInvocation oldInvocation = INVOCATION_THREAD_LOCAL.get();
        INVOCATION_THREAD_LOCAL.set(mi);
        try {
            return mi.proceed();
        }
        finally {
            //ThreadLocal的set null
            INVOCATION_THREAD_LOCAL.set(oldInvocation);
        }
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    /**
     * 这里确保了此拦截器会在偏序排序时放在第一位
     * @return
     */
    @Override
    public int getOrder()
    {
        return Ordered.HIGHEST_PRECEDENCE+1;
    }
}
