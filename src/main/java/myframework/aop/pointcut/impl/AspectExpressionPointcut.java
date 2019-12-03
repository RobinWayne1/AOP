package myframework.aop.pointcut.impl;

import myframework.aop.pointcut.ClassFilter;
import myframework.aop.pointcut.MethodMatcher;
import myframework.aop.pointcut.PointCut;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Robin
 * @date 2019/11/28 -21:34
 */
//切点类,声明切点方法符合的类和方法
public class AspectExpressionPointcut implements ClassFilter, MethodMatcher, PointCut
{
    private final  Class<?> returnType;

    private final  Class<?>[]parameterType;

    private final Integer  parameterCount;

    private final String expression;

    private final String methodName;

    private final Class<?> targetClass;



    public AspectExpressionPointcut(Class<?> returnType, Class<?>[] parameterType, Integer parameterCount, String expression)
    {
        this.returnType = returnType;
        this.parameterType = parameterType;
        this.parameterCount = parameterCount;
        this.expression = expression;
    }

    @Override
    public MethodMatcher getMethodMatcher()
    {
        return this;
    }

    @Override
    public ClassFilter getClassFilter()
    {
        return this;
    }

    @Override
    public boolean matches(Class targetClass)
    {
        return false;
    }

    /**
     * 用来和受到代理的方法匹配并返回结果
     *
     * @param proxyMethod
     * @return
     */
    @Override
    public boolean matches(Method proxyMethod)
    {
        return false;
    }
}
