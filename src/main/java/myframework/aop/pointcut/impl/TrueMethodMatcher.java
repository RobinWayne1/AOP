package myframework.aop.pointcut.impl;

import myframework.aop.pointcut.MethodMatcher;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/12/10 -16:29
 */
public class TrueMethodMatcher implements MethodMatcher
{
    public static final TrueMethodMatcher INSTANCE=new TrueMethodMatcher();

    /**
     * 保证单例
     */
    private TrueMethodMatcher()
    {}
    /**
     * 用来和受到代理的方法匹配并返回结果
     *
     * @param proxyMethod
     * @return
     */
    @Override
    public boolean matches(Method proxyMethod)
    {
        return true;
    }
}
