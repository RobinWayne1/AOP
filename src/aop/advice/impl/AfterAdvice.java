package aop.advice.impl;

import aop.advice.Advice;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/11/28 -17:57
 */
public class AfterAdvice implements Advice
{
    private final Method m;

    public AfterAdvice(Method m)
    {
        this.m = m;
    }
}
