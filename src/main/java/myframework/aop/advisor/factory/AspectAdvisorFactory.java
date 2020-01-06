package myframework.aop.advisor.factory;

import myframework.aop.advice.Advice;
import myframework.aop.advisor.Advisor;
import myframework.aop.factory.MetaDataAwareAspectInstanceFactory;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 存放advisor,用于在invoke()时从factory获取advisor
 *
 * @author Robin
 * @date 2019/12/1 -22:50
 */
public interface AspectAdvisorFactory
{
    List<Advisor> getAdvisors(MetaDataAwareAspectInstanceFactory aif);

    Advisor getAdvisor(Method candidateAdviceMethod, MetaDataAwareAspectInstanceFactory aif,int declaringOrder);

    /**
     *  <p>1.得到AOP注解</p>
     *  <p>2.根据AOP注解类型生成Advice</p>
     *   <p>3.调用Advice的calculateArgumentBindings()方法,判断是否绑定JoinpPoint(设置一个flag),然后绑定其他参数如
     *     afterReturnin的参数</p>
     *
     * @param candidateAdviceMethod
     * @param aexp
     * @param aif
     * @return
     */
    Advice getAdvice(Method candidateAdviceMethod, AspectExpressionPointcut aexp,MetaDataAwareAspectInstanceFactory aif,int declaringOrder);

    boolean isAspect(Class<?> beanType);
}
