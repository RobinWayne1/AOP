package myframework.aop.advice.impl;

import myframework.aop.advice.Advice;
import myframework.aop.advice.AspectAdvice;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/11/28 -17:57
 */
public class AfterAdvice implements AspectAdvice
{
    private final Method m;

    public AfterAdvice(Method m)
    {
        this.m = m;
    }

    @Override
    public Object invoke()
    {
        return null;
    }
}
