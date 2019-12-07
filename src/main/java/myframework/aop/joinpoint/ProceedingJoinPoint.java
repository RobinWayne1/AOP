package myframework.aop.joinpoint;

/**
 * 用作给环绕通知传递JoinPoint,并使其有调用MethodInvocation中的proceed()功能
 * ,使得在执行环绕通知后重新进入拦截器链
 * @author Robin
 * @date 2019/12/7 -11:31
 */
public interface ProceedingJoinPoint extends JoinPoint
{

     Object proceed() throws Throwable;

    /**
     * 给环绕通知一个机会修改入参
     * @param args
     * @return
     */
     Object proceed(Object[]args);
}
