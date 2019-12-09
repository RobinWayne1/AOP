package myframework.aop.advisor;

import myframework.aop.pointcut.impl.AspectExpressionPointcut;

/**
 * @author Robin
 * @date 2019/12/10 -0:24
 */
public interface PointcutAdvisor extends Advisor
{
    /**
     * 由子类持有切点
     * @return
     */
    AspectExpressionPointcut getPointcut();
}
