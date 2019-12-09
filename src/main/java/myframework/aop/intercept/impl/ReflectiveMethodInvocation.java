package myframework.aop.intercept.impl;


import myframework.aop.advised.AdvisedSupport;
import myframework.aop.intercept.Joinpoint;
import myframework.aop.intercept.MethodInvocation;
import myframework.aop.intercept.ProxyMethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Robin
 * @date 2019/11/28 -15:40
 */
public class ReflectiveMethodInvocation implements ProxyMethodInvocation, InvocationHandler
{

    private Map<String, Object> userAttributes;

    private final AdvisedSupport config;

    public ReflectiveMethodInvocation(AdvisedSupport config)
    {
        this.config = config;
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
        return null;
    }

    @Override
    public Object setAttribute(String key, Object value)
    {
        return null;
    }

    /**
     * 获得当前连接点的目标方法
     *
     * @return
     */
    @Override
    public Method getMethod()
    {
        return null;
    }

    /**
     * 获得当前连接点目标方法的入参值
     *
     * @return
     */
    @Override
    public Object[] getArgument()
    {
        return new Object[0];
    }

    @Override
    public Object proceed() throws Throwable
    {
        //拦截器链的控制
        //调用拦截器链的invoke
        return null;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {

        return null;
    }
}
