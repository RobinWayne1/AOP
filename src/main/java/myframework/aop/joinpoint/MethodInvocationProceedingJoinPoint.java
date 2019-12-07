package myframework.aop.joinpoint;

/**
 * @author Robin
 * @date 2019/12/7 -11:44
 */
public class MethodInvocationProceedingJoinPoint implements ProceedingJoinPoint
{

    private Object[] args;

//    private final ProxyMethodInvocation methodInvocation;

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
