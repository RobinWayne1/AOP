package myframework.aop.intercept.impl;


import myframework.aop.intercept.Joinpoint;
import myframework.aop.intercept.MethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/11/28 -15:40
 */
public class ReflectiveMethodInvocation implements MethodInvocation, InvocationHandler
{
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
