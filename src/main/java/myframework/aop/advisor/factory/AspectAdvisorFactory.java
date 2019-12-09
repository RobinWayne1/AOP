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

    Advisor getAdvisor(Method candidateAdviceMethod, MetaDataAwareAspectInstanceFactory aif);

    Advice getAdvice(Method candidateAdviceMethod, AspectExpressionPointcut aexp,MetaDataAwareAspectInstanceFactory aif);

    boolean isAspect(Class<?> beanType);
}
