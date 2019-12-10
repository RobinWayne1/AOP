package myframework.aop.pointcut.impl;

import myframework.aop.advisor.PointcutAdvisor;
import myframework.aop.pointcut.ClassFilter;
import myframework.aop.pointcut.MethodMatcher;
import myframework.aop.pointcut.Pointcut;

/**
 * @author Robin
 * @date 2019/12/10 -16:37
 */
public class TruePointcut implements Pointcut
{
    public static final TruePointcut INSTANCE=new TruePointcut();

    /**
     * 保证单例
     */
    private TruePointcut()
    {}
    @Override
    public MethodMatcher getMethodMatcher()
    {
        return MethodMatcher.TRUE;
    }

    @Override
    public ClassFilter getClassFilter()
    {
        return ClassFilter.TRUE;
    }
}
