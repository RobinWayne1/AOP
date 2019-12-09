package myframework.aop.advice.impl;

import myframework.aop.advice.MethodInterceptor;

/**
 * 用于存放线程封闭的MethodInvocation,这样可以从MethodInvocation中得到attr信息,
 * 记得一定要在finally set(null),否则会造成ThreadLocalOOM
 * @author Robin
 * @date 2019/12/7 -16:59
 */
public class ExposeInvocationInterceptor implements MethodInterceptor
{
    @Override
    public Object invoke()
    {
        return null;
    }
}
