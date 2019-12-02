package myframework.aop.advisor.factory;

import myframework.aop.advice.Advice;
import myframework.aop.advisor.Advisor;
import myframework.aop.factory.AspectInstanceFactory;

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
    List<Advisor> getAdvisors(AspectInstanceFactory aif);

    Advisor getAdvisor(Method candidateAdviceMethod,AspectInstanceFactory aif);

    Advice getAdvice();

    boolean isAspect(String beanName);
}
