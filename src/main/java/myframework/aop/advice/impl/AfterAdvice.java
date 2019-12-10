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
public class AfterAdvice extends AbstractAspectAdvice implements MethodInterceptor
{


    public AfterAdvice(Method aspectJAdviceMethod, AspectExpressionPointcut pointcut, AspectInstanceFactory aspectInstanceFactory)
    {
        super(aspectJAdviceMethod, pointcut, aspectInstanceFactory);
    }

    @Override
    public Object invoke(MethodInvocation invocation)
    {
        return null;
    }
}
