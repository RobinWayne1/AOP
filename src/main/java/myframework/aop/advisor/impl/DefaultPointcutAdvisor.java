package myframework.aop.advisor.impl;

import myframework.aop.advice.Advice;
import myframework.aop.advisor.PointcutAdvisor;
import myframework.aop.pointcut.Pointcut;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;
import myframework.aop.pointcut.impl.TruePointcut;

/**
 * @author Robin
 * @date 2019/12/10 -17:01
 */
public class DefaultPointcutAdvisor implements PointcutAdvisor
{
    private Pointcut pointcut=Pointcut.TRUE;

    private Advice advice;

    public DefaultPointcutAdvisor()
    {
    }

    public DefaultPointcutAdvisor(Pointcut pointcut, Advice advice)
    {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    public DefaultPointcutAdvisor(Advice advice)
    {
        this.advice = advice;
    }

    public void setPointcut(Pointcut pointcut)
    {
        this.pointcut = pointcut;
    }

    public void setAdvice(Advice advice)
    {
        this.advice = advice;
    }

    /**
     * 由子类持有切点
     *
     * @return
     */

    @Override
    public Pointcut getPointcut()
    {
        return pointcut;
    }

    /**
     * 由子类持有增强
     *
     * @return
     */
    @Override
    public Advice getAdvice()
    {
        return advice;
    }
}
