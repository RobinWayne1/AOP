package myframework.aop.advice;

import myframework.aop.advice.impl.ExposeInvocationInterceptor;
import myframework.aop.factory.AspectInstanceFactory;
import myframework.aop.framework.proxy.intercept.MethodInvocation;
import myframework.aop.framework.proxy.intercept.ProxyMethodInvocation;
import myframework.aop.joinpoint.JoinPoint;
import myframework.aop.joinpoint.MethodInvocationProceedingJoinPoint;
import myframework.aop.joinpoint.ProceedingJoinPoint;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;
import myframework.exception.AopInvocationException;
import myframework.exception.UnknowedOperationException;

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

    protected Method aspectAdviceMethod;

    /**
     * 用作getJoinPointMatch方法,暂时不清楚有什么用
     */
    private final AspectExpressionPointcut pointcut;

    /**
     * 用来获取切面实例以invoke增强方法
     */
    private final AspectInstanceFactory aspectInstanceFactory;

    /**
     * 由于这个类需要的是{@link AspectInstanceFactory},所以无法获得aspectName(看接口)
     */
    private String aspectName = "";

    /**
     * 用来检测是否增强方法是否需要JoinPoint参数注入,且这是制定了参数位置index的,即排在args数组的第几位
     */
    private int joinPointArgumentIndex = -1;

    public AbstractAspectAdvice(
            Method aspectAdviceMethod, AspectExpressionPointcut pointcut, AspectInstanceFactory aspectInstanceFactory)
    {
        this.declaringClass = aspectAdviceMethod.getDeclaringClass();
        this.methodName = aspectAdviceMethod.getName();
        this.parameterTypes = aspectAdviceMethod.getParameterTypes();
        this.aspectAdviceMethod = aspectAdviceMethod;
        this.pointcut = pointcut;
        this.aspectInstanceFactory = aspectInstanceFactory;
    }

    protected JoinPoint getJoinPoint()
    {
        return currentJoinPoint();
    }

    /**
     * 知道为什么不用传入mi的方法了,他用static修饰,本意应该是想让用户使用这个方法,而让用户使用怎么可能让其传入
     * 一个mi,所以,就这样
     *
     * @param mi
     * @return
     */
    public static JoinPoint currentJoinPoint()
    {
        MethodInvocation mi = ExposeInvocationInterceptor.currentInvocation();
        if (!(mi instanceof ProxyMethodInvocation))
        {
            throw new UnknowedOperationException(mi.getClass() + "is not a is not a framework ProxyMethodInvocation");
        }
        ProxyMethodInvocation pmi = (ProxyMethodInvocation) mi;
        JoinPoint jp = (JoinPoint) pmi.getAttribute(JOIN_POINT_KEY);
        if (jp == null)
        {
            jp = new MethodInvocationProceedingJoinPoint(pmi);
            pmi.setAttribute(JOIN_POINT_KEY, jp);
        }
        return jp;
    }

    /**
     * 目前只支持JoinPoint返回,这个方法还有很重点的后半部分没有看
     *
     * @return
     */
    protected Object[] argBinding(JoinPoint jp)
    {
        calculateArgumentBindings();
        Object[] adviceInvocationArgs = new Object[this.parameterTypes.length];
        int numBound = 0;
        if (this.joinPointArgumentIndex != -1)
        {
            adviceInvocationArgs[joinPointArgumentIndex] = jp;
            numBound++;
        }
        if (numBound != this.parameterTypes.length)
        {
            throw new AopInvocationException("Unknown type advice parameters to bind");
        }
        return adviceInvocationArgs;
    }

    /**
     * 实验:把mi从子类传到这个方法,又这个方法创建joinPoint,而不选择使用ExposeInvocationInterceptor
     *
     * @return
     */
    protected Object invokeAdviceMethod()
    {
        return invokeAdviceMethodWithGivenArgs(argBinding(getJoinPoint()));
    }

    /**
     * 这里调用的是切面增强方法,所以是从AspectInstanceFactory中取出bean,而不需要MethodInvocation的target
     *
     * @param args
     * @return
     */
    protected Object invokeAdviceMethodWithGivenArgs(Object[] args)
    {
        try
        {
            this.aspectAdviceMethod.setAccessible(true);
            return this.aspectAdviceMethod.invoke(this.aspectInstanceFactory.getAspectInstance(),args);
        }
        catch (IllegalArgumentException ex) {
            throw new AopInvocationException("Mismatch on arguments to advice method [" +
                    this.aspectAdviceMethod + "]; pointcut expression [" +
                    this.pointcut.getExpression() + "]", ex);
        }
        catch(ReflectiveOperationException ex)
        {
            throw new AopInvocationException(ex.getMessage(),ex);
        }

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
     * 此方法主要用于绑定如afterReturning等(return())参数,JoinPoint则是在argBinding绑定的
     * 此方法用作以后扩展afterReturning
     * <p>
     * 而此方法也要判断是否有joinPoint或proceedingJoinPoint
     */
    public final synchronized void calculateArgumentBindings()
    {
        if (!this.argumentsIntrospected || this.parameterTypes.length == 0)
        {
            return;
        }
        int numUnboundArgs = this.parameterTypes.length;
        Class<?>[] parameterTypes = this.aspectAdviceMethod.getParameterTypes();
        //判断JoinPoint参数存在顺便setFlag
        if (maybeBindJoinPoint(parameterTypes[0]) || maybeBindProceedingJoinPoint(parameterTypes[0]))
        {
            //剩余的numUnboundArgs由其他参数绑定方法使用,以length-numUnboundArgs找到参数在数组的位置
            numUnboundArgs--;
        }
        //暂不支持其他参数绑定,这里spring绝对不只是判断是否为0,所以这里写法应该是有误的,不过不影响
        if (numUnboundArgs != 0)
        {
            throw new AopInvocationException("Unknown type advice parameters to bind");
        }
    }

    private boolean maybeBindJoinPoint(Class<?> candidateParameterType)
    {
        if (JoinPoint.class == candidateParameterType)
        {
            this.joinPointArgumentIndex = 0;
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 巧妙的方法,如果不重写该方法则默认不是aroundAdvice,则不予绑定proceedingJoinPoint
     *
     * @return
     */
    protected boolean supportsProceedingJoinPoint()
    {
        return false;
    }

    private boolean maybeBindProceedingJoinPoint(Class<?> candidateParameterType)
    {
        if (ProceedingJoinPoint.class == candidateParameterType)
        {
            if (!supportsProceedingJoinPoint())
            {
                throw new AopInvocationException("ProceedingJoinPoint is only supported for around advice,change your advice's argument");
            }
            this.joinPointArgumentIndex = 0;
            return true;
        } else
        {
            return false;
        }
    }
}
