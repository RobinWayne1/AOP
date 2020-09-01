package myframework.aop.framework.proxy.intercept;


/**
 * @author Robin
 * @date 2019/12/9 -20:34
 */
public interface ProxyMethodInvocation extends MethodInvocation
{
    /**
     * 给用户存放整个连接点执行过程的键值对，如增强方法中需要获得的JoinPoint就会存放到这里
     * @param key
     * @return
     */
    Object getAttribute(String key);

    Object setAttribute(String key,Object value);

    /**
     * 得到代理对象
     * @return
     */
    Object getProxy();

    /**
     * 主要用于调用过程中更改入参
     * @param arguments
     */
    void setArguments(Object[] arguments);
}
