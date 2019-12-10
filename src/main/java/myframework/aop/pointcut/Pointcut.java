package myframework.aop.pointcut;

import myframework.aop.pointcut.impl.TruePointcut;

/**
 * @author Robin
 * @date 2019/12/3 -23:20
 */
public interface Pointcut
{
    /**
     * 目标方法为任意参数都可以符合的pointcut表达式
     */
     Class<?>[] ALL_PARAMETERS=null;

    MethodMatcher getMethodMatcher();

    ClassFilter getClassFilter();

    /**
     * 用于对某些要对所有invoke都适用的增强就要使用此pointcut,
     * 如{@link myframework.aop.advice.impl.ExposeInvocationInterceptor}
     */
    Pointcut TRUE= TruePointcut.INSTANCE;
}
