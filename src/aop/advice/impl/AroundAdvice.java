package aop.advice.impl;

import aop.advice.Advice;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/11/28 -17:57
 */
public class AroundAdvice implements Advice
{
    private final Method m;

    public AroundAdvice(Method m)
    {
        this.m = m;
    }

    @Override
    public Object invoke()
    {
        return null;
    }
}
