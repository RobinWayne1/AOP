package myframework.aop.pointcut;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/11/28 -21:38
 */
public interface MethodMatcher
{
    /**
     * 用来和受到代理的方法匹配并返回结果
     * @param proxyMethod

     * @return
     */
   boolean matches(Method proxyMethod);
}
