package myframework.aop.advice.impl;

import myframework.aop.advice.Advice;
import myframework.aop.advice.AspectAdvice;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/11/28 -17:57
 */
public class AroundAdvice implements AspectAdvice
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
