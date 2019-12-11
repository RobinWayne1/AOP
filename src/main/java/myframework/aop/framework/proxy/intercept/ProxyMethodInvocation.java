package myframework.aop.framework.proxy.intercept;


/**
 * @author Robin
 * @date 2019/12/9 -20:34
 */
public interface ProxyMethodInvocation extends MethodInvocation
{
    /**
     * 给用户存放整个连接点执行过程的
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
