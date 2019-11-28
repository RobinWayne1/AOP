package aop.advisor.impl;

import aop.advice.Advice;
import aop.advisor.Advisor;
import aop.pointcut.Pointcut;

/**
 * @author Robin
 * @date 2019/11/28 -23:22
 */
public class PointcutAdvisor implements Advisor
{

    private final Pointcut pointcut=new Pointcut();

    private  Advice advice=EMPTY_ADVICE;

    /**
     * 由子类持有切点
     *
     * @return
     */
    @Override
    public Pointcut getPointcut()
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