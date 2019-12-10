package myframework.aop.advice.impl;

import myframework.aop.advice.AbstractAspectAdvice;
import myframework.aop.advice.MethodInterceptor;
import myframework.aop.factory.AspectInstanceFactory;
import myframework.aop.intercept.MethodInvocation;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/11/28 -17:57
 */
public class AroundAdvice extends AbstractAspectAdvice implements MethodInterceptor
{
    public AroundAdvice(Method aspectJAdviceMethod, AspectExpressionPointcut pointcut, AspectInstanceFactory aspectInstanceFactory)
    {
        super(aspectJAdviceMethod, pointcut, aspectInstanceFactory);
    }

    /**
     * 巧妙的方法,如果不重写该方法则默认不是aroundAdvice,则不予绑定proceedingJoinPoint
     *
     * @return
     */
    @Override
    protected boolean supportsProceedingJoinPoint()
    {
        return true;
    }

    @Override
    public Object invoke(MethodInvocation invocation)
    {
        return null;
    }
}
