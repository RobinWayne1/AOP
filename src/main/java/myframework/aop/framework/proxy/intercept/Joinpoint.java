package myframework.aop.framework.proxy.intercept;

/**
 * 拦截调用时的连接点,即调用proceed()可以继续执行拦截调用
 * @author Robin
 * @date 2019/11/28 -15:38
 */
public interface Joinpoint
{
    Object proceed() throws Throwable;
}
