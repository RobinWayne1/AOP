package myframework.aop.joinpoint;

import myframework.aop.framework.proxy.intercept.ProxyMethodInvocation;
import myframework.exception.AopInvocationException;

/**
 *
 *
 * 此类用作给aroundAdvice中的ProceedingJoinPoint赋值
 *
 * ps:不止环绕通知,如果其他类型通知中参数需要JoinPoint都会注入此类
 *
 * @author Robin
 * @date 2019/12/7 -11:44
 */
public class MethodInvocationProceedingJoinPoint implements ProceedingJoinPoint
{

    private Object[] args;

    private final ProxyMethodInvocation methodInvocation;

    public MethodInvocationProceedingJoinPoint(ProxyMethodInvocation mi)
    {
        this.methodInvocation=mi;
    }

    /**
     * 给环绕通知一个机会修改入参
     *
     * @param args
     * @return
     */
    @Override
    public Object proceed(Object[] args)throws Throwable
    {
        if (args.length != this.methodInvocation.getArguments().length) {
            throw new AopInvocationException("Arguments length inconformity");
        }
        this.methodInvocation.setArguments(args);
        return this.methodInvocation.proceed();
    }

    @Override
    public Object proceed() throws Throwable
    {
        return this.methodInvocation.proceed();
    }

    @Override
    public Object getThis()
    {
        return this.methodInvocation.getProxy();
    }

    /**
     * 如果此处直接发布的话则是不安全的发布,得到该对象的增强方法就可以直接任意改你的参数数组
     * @return
     */
    @Override
    public Object[] getArgs()
    {
        if(this.args==null)
        {
            this.args=this.methodInvocation.getArguments().clone();
        }
        return this.args;
    }
}
