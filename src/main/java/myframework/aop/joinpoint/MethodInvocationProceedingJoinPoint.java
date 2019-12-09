package myframework.aop.joinpoint;

/**
 * @author Robin
 * @date 2019/12/7 -11:44
 */
public class MethodInvocationProceedingJoinPoint implements ProceedingJoinPoint
{


    private Object[] args;

//    private final ProxyMethodInvocation methodInvocation;


    /**
     * 给环绕通知一个机会修改入参
     *
     * @param args
     * @return
     */
    @Override
    public Object proceed(Object[] args)
    {
        return null;
    }

    @Override
    public Object proceed() throws Throwable
    {
        return null;
    }

    @Override
    public Object getThis()
    {
        return null;
    }

    @Override
    public Object[] getArgs()
    {
        return new Object[0];
    }
}
