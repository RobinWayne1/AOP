package myframework.aop.advice;

import myframework.aop.factory.AspectInstanceFactory;
import myframework.aop.joinpoint.JoinPoint;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/12/7 -10:23
 */
public class AbstractAspectAdvice implements Advice
{

    protected static final String JOIN_POINT_KEY = JoinPoint.class.getName();


    /**
     * 作为一个flag,判断arg是否已经binding过了,第一种是在生成advice之后调用advice.calculateArgumentBindings()绑定过了
     * ,第二种是在运行时调用argBinding()时进行绑定
     */
    private boolean argumentsIntrospected = false;

    private final Class<?> declaringClass;

    private final String methodName;
    /**
     * 增强方法的参数
     */
    private final Class<?>[] parameterTypes;

    protected Method aspectJAdviceMethod;

    /**
     * 用作getJoinPointMatch方法,暂时不清楚有什么用
     */
    private final AspectExpressionPointcut pointcut;

    /**
     * 用来获取切面实例以invoke增强方法
     */
    private final AspectInstanceFactory aspectInstanceFactory;

    /**
     * 由于这个类需要的是{@link AspectInstanceFactory},所以无法获得aspectName
     */
    private String aspectName = "";

    public AbstractAspectAdvice(
            Method aspectJAdviceMethod, AspectExpressionPointcut pointcut, AspectInstanceFactory aspectInstanceFactory)
    {
        this.declaringClass = aspectJAdviceMethod.getDeclaringClass();
        this.methodName = aspectJAdviceMethod.getName();
        this.parameterTypes = aspectJAdviceMethod.getParameterTypes();
        this.aspectJAdviceMethod = aspectJAdviceMethod;
        this.pointcut = pointcut;
        this.aspectInstanceFactory = aspectInstanceFactory;
    }

    public static JoinPoint currentJoinPoint()
    {
    }

    protected Object[] argBinding()
    {

    }

    protected Object invokeAdviceMethod()
    {

    }

    protected Object invokeAdviceMethodWithGivenArgs(Object[] args)
    {
    }

    public String getAspectName()
    {
        return aspectName;
    }

    public void setAspectName(String aspectName)
    {
        this.aspectName = aspectName;
    }

    /**
     * 此方法主要用于绑定如afterReturning等参数,JoinPoint则是在argBinding绑定的
     * 此方法用作以后扩展afterReturning
     */
    public final synchronized void calculateArgumentBindings() {}
}
