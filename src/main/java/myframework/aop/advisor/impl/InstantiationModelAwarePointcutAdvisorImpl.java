package myframework.aop.advisor.impl;

import myframework.aop.advice.Advice;
import myframework.aop.advisor.Advisor;
import myframework.aop.advisor.PointcutAdvisor;
import myframework.aop.advisor.factory.AspectAdvisorFactory;
import myframework.aop.factory.MetaDataAwareAspectInstanceFactory;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;

import java.lang.reflect.Method;

/**
 * spring的这个类由isLazy()等实现,暂时不清楚此类的作用,本来想用
 * DefaultPointAdvisor,但是这个名字有别的作用
 * @author Robin
 * @date 2019/11/28 -23:22
 */
public class InstantiationModelAwarePointcutAdvisorImpl implements PointcutAdvisor
{

    private final AspectExpressionPointcut pointcut;

    private Advice advice;

    private final AspectAdvisorFactory advisorFactory;

    private final MetaDataAwareAspectInstanceFactory aspectInstanceFactory;

    private final Method adviceMethod;

    private final String aspectName;

    public InstantiationModelAwarePointcutAdvisorImpl(AspectExpressionPointcut pointcut, AspectAdvisorFactory advisorFactory, MetaDataAwareAspectInstanceFactory aspectInstanceFactory, Method adviceMethod, String aspectName)
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
