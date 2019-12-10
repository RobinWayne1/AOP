package myframework.aop.pointcut;

import myframework.aop.pointcut.impl.TrueMethodMatcher;

import java.lang.reflect.Method;

/**
 * 用于对目标方法和pointcut进行匹配
 *
 * @author Robin
 * @date 2019/11/28 -21:38
 */
public interface MethodMatcher
{
    /**
     * 用来和受到代理的方法匹配并返回结果
     *
     * @param proxyMethod
     * @return
     */
    boolean matches(Method proxyMethod);

    MethodMatcher TRUE = TrueMethodMatcher.INSTANCE;
}
