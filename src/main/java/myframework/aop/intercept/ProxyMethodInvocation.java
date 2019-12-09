package myframework.aop.intercept;

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
}
