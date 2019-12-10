package myframework.aop.intercept;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/12/7 -10:17
 */
public interface MethodInvocation extends Joinpoint
{
    /**
     * 获得当前连接点的目标方法
     * @return
     */
    Method getMethod();

    /**
     * 获得当前连接点目标方法的入参值
     * @return
     */
    Object[] getArguments();
}
