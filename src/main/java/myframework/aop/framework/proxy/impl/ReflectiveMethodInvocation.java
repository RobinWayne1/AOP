package myframework.aop.framework.proxy.impl;


import myframework.aop.advice.MethodInterceptor;
import myframework.aop.framework.proxy.intercept.ProxyMethodInvocation;
import myframework.util.AopUtil;


import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Robin
 * @date 2019/11/28 -15:40
 */
public class ReflectiveMethodInvocation implements ProxyMethodInvocation
{

    private Map<String, Object> userAttributes = new ConcurrentHashMap<>(16);

    /**
     * 控制拦截器链的index
     */
    private int currentInterceptorIndex = -1;

    //代理对象
    private final Object proxy;

    //被代理的原对象
    /**
     * 请确认好InvocationHandler和Proxy的区别,没有目标对象是调用不了目标方法的
     */
    private final Object target;

    private final Class<?> targetClass;

    private final List<MethodInterceptor> chain;

    private Object[] args;

    private final Method targetMethod;

    protected ReflectiveMethodInvocation(Object proxy, Object target, Class<?> targetClass, List<MethodInterceptor> chain, Object[] args, Method targetMethod)
    {
        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.chain = chain;
        this.args = args;
        this.targetMethod = targetMethod;
    }

    /**
     * 给用户存放整个连接点执行过程的
     *
     * @param key
     * @return
     */
    @Override
    public Object getAttribute(String key)
    {
        return this.userAttributes.get(key);
    }

    @Override
    public Object setAttribute(String key, Object value)
    {
        return this.userAttributes.put(key, value);
    }

    /**
     * 得到代理对象
     *
     * @return
     */
    @Override
    public Object getProxy()
    {
        return this.proxy;
    }

    /**
     * 主要用于调用过程中更改入参
     *
     * @param arguments
     */
    @Override
    public void setArguments(Object[] arguments)
    {
        this.args = arguments;
    }

    /**
     * 获得当前连接点的目标方法
     *
     * @return
     */
    @Override
    public Method getMethod()
    {
        return this.targetMethod;
    }

    /**
     * 获得当前连接点目标方法的入参值
     *
     * @return
     */
    @Override
    public Object[] getArguments()
    {
        return this.args;
    }

    /**
     *  拦截器链的控制
     *  调用拦截器链的invoke
     * @return
     * @throws Throwable
     */
    @Override
    public Object proceed() throws Throwable
    {
        if(this.currentInterceptorIndex==this.chain.size() - 1)
        {
            AopUtil.invokeJoinpointUsingReflection(this.target,this.targetMethod,this.args);
        }
        MethodInterceptor currentMethodInterceptor=this.chain.get(++this.currentInterceptorIndex);

        /**
         * 链执行开始,由于调用目标方法是在当前proceed()中,所以advice全都是return mi.proceed(),除了around
         * 是变相调用proceed()
         */
        return currentMethodInterceptor.invoke(this);
    }


}
