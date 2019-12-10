package myframework.aop.intercept.impl;


import com.sun.istack.internal.Nullable;
import myframework.aop.advice.MethodInterceptor;
import myframework.aop.advised.AdvisedSupport;
import myframework.aop.intercept.Joinpoint;
import myframework.aop.intercept.MethodInvocation;
import myframework.aop.intercept.ProxyMethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Robin
 * @date 2019/11/28 -15:40
 */
public class ReflectiveMethodInvocation implements ProxyMethodInvocation
{

    private Map<String, Object> userAttributes;

    //代理对象
    private final Object proxy;

    //被代理的原对象
    /**
     * 请确认好InvocationHandler和Proxy的区别,没有目标对象是调用不了目标方法的
     */
    private final Object target;

    private final Class<?> targetClass;

    private final List<MethodInterceptor> chain;

    private  Object[] args;

    private final Method targetMethod;

    protected ReflectiveMethodInvocation(Map<String, Object> userAttributes, Object proxy, Object target, Class<?> targetClass, List<MethodInterceptor> chain, Object[] args, Method targetMethod)
    {
        this.userAttributes = userAttributes;
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
        return this.userAttributes.put(key,value);
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
        this.args=arguments;
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

    @Override
    public Object proceed() throws Throwable
    {
        //拦截器链的控制
        //调用拦截器链的invoke
        return null;
    }


}
