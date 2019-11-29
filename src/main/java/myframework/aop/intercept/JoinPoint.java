package myframework.aop.intercept;

/**
 * @author Robin
 * @date 2019/11/28 -15:38
 */
public interface JoinPoint
{
    Object proceed() throws Throwable;
}
