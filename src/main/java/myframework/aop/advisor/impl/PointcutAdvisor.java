package myframework.aop.advisor.impl;

import myframework.aop.advice.Advice;
import myframework.aop.advisor.Advisor;
import myframework.aop.advisor.factory.AspectAdvisorFactory;
import myframework.aop.factory.AspectInstanceFactory;
import myframework.aop.factory.MetaDataAwareAspectInstanceFactory;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/11/28 -23:22
 */
public class PointcutAdvisor implements Advisor
{

    private final AspectExpressionPointcut pointcut;

    private Advice advice=EMPTY_ADVICE;

    private final AspectAdvisorFactory advisorFactory;

    private final MetaDataAwareAspectInstanceFactory aspectInstanceFactory;

    private final Method adviceMethod;

    private final String aspectName;

    public PointcutAdvisor(AspectExpressionPointcut pointcut, AspectAdvisorFactory advisorFactory, MetaDataAwareAspectInstanceFactory aspectInstanceFactory, Method adviceMethod, String aspectName)
    {
        this.pointcut = pointcut;
        this.advisorFactory = advisorFactory;
        this.aspectInstanceFactory = aspectInstanceFactory;
        this.adviceMethod = adviceMethod;
        this.aspectName = aspectName;
        this.advice=instantiateAdvice();
    }

    private Advice instantiateAdvice()
    {
        return this.advisorFactory.getAdvice(this.adviceMethod,this.pointcut,this.aspectInstanceFactory);
    }

    /**
     * 由子类持有切点
     *
     * @return
     */
    @Override
    public AspectExpressionPointcut getPointcut()
    {
        return this.pointcut;
    }

    /**
     * 由子类持有增强
     *
     * @return
     */
    @Override
    public Advice getAdvice()
    {
        return this.advice;
    }
}
