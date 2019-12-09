package myframework.aop.proxy.impl;

import myframework.aop.advice.MethodInterceptor;
import myframework.aop.advised.AdvisedSupport;
import myframework.aop.proxy.AopProxy;
import myframework.util.AopUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 需要存放chainFactory,每个实例一个JdkDynamicAopProxy,只存放以本实例为targetclass的advisor,
 * 其中一个JdkDynamicAopProxy有多个拦截器链,不同的拦截器链对应不同的方法
 *
 * @author Robin
 * @date 2019/11/30 -23:21
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler
{
    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport config)
    {
        this.advised = config;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        List<MethodInterceptor> chain=this.advised.getInterceptorsAndDynamicInterceptionAdvice(method,this.advised.getTarget().getClass());
        /**
         * 处理代理类中的方法没有受到增强的情况
         */
        if(chain.isEmpty())
        {
            AopUtil.invokeJoinpointUsingReflection(proxy,method,args);
        }
        return null;
    }

    /**
     * Proxy.newInstance()
     *
     * @return
     */
    @Override
    public Object getProxy()
    {
        return getProxy(Thread.currentThread().getContextClassLoader());
    }

    public  Object getProxy(ClassLoader contextClassLoader)
    {
        List<Class<?>> proxyInterfaces= advised.getInterfaces();
        return Proxy.newProxyInstance(contextClassLoader,proxyInterfaces.toArray(new Class[proxyInterfaces.size()]),this);
    }
}
