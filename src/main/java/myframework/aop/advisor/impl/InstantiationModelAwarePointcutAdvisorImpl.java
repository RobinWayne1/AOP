package myframework.aop.advisor.impl;

import myframework.aop.AspectPrecedenceInformation;
import myframework.aop.advice.Advice;
import myframework.aop.advisor.Advisor;
import myframework.aop.advisor.PointcutAdvisor;
import myframework.aop.advisor.factory.AspectAdvisorFactory;
import myframework.aop.factory.MetaDataAwareAspectInstanceFactory;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;

import java.lang.reflect.Method;

/**
 * advisor实现类,封装pointcut和advice
 * <p></p>
 * 本来想用DefaultPointAdvisor,但是这个名字有别的用处
 * @author Robin
 * @date 2019/11/28 -23:22
 */
public class InstantiationModelAwarePointcutAdvisorImpl implements PointcutAdvisor, AspectPrecedenceInformation
{

    private final AspectExpressionPointcut pointcut;

    private Advice advice;

    private final AspectAdvisorFactory advisorFactory;

    private final MetaDataAwareAspectInstanceFactory aspectInstanceFactory;

    private final Method adviceMethod;

    private final String aspectName;

    /**
     * 在切面的有advice注解的方法的定义顺序
     */
    private final int declaringOrder;

    public InstantiationModelAwarePointcutAdvisorImpl(AspectExpressionPointcut pointcut, AspectAdvisorFactory advisorFactory, MetaDataAwareAspectInstanceFactory aspectInstanceFactory, Method adviceMethod, String aspectName,int declaringOrder)
    {
        this.pointcut = pointcut;
        this.advisorFactory = advisorFactory;
        this.aspectInstanceFactory = aspectInstanceFactory;
        this.adviceMethod = adviceMethod;
        this.aspectName = aspectName;
        this.declaringOrder=declaringOrder;
        this.advice=instantiateAdvice();
    }

    private Advice instantiateAdvice()
    {
        return this.advisorFactory.getAdvice(this.adviceMethod,this.pointcut,this.aspectInstanceFactory,this.declaringOrder);
    }

    /**
     * Return the name of the aspect (bean) in which the advice was declared.
     */
    @Override
    public String getAspectName()
    {
        return null;
    }

    /**
     * Return the declaration order of the advice member within the aspect.
     */
    @Override
    public int getDeclarationOrder()
    {
        return this.declaringOrder;
    }

    /**
     * Return whether this is a before advice.
     */
    @Override
    public boolean isBeforeAdvice()
    {
        return false;
    }

    /**
     * Return whether this is an after advice.
     */
    @Override
    public boolean isAfterAdvice()
    {
        return false;
    }


    /**
     * 获得该advisor所在切面的order
     */
    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder()
    {
        return 0;
    }

    /**
     * 由子类持有切点
     *
     * @return
     */
    @Override
    public AspectExpressionPointcut getPointcut()
    {
        return this.pointcut;
    }

    /**
     * 由子类持有增强
     *
     * @return
     */
    @Override
    public Advice getAdvice()
    {
        return this.advice;
    }
}
