package aop.intercept.impl;


import aop.intercept.JoinPoint;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/11/28 -15:40
 */
public class ReflectiveMethodInvocation implements JoinPoint, InvocationHandler
{
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
