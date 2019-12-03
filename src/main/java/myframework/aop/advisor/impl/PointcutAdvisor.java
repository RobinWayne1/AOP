package myframework.aop.advisor.impl;

import myframework.aop.advice.Advice;
import myframework.aop.advisor.Advisor;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;

/**
 * @author Robin
 * @date 2019/11/28 -23:22
 */
public class PointcutAdvisor implements Advisor
{

    private final AspectExpressionPointcut pointcut;

    private Advice advice=EMPTY_ADVICE;

    public PointcutAdvisor(AspectExpressionPointcut pointcut, Advice advice)
    {
        this.pointcut = pointcut;
        this.advice = advice;
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
