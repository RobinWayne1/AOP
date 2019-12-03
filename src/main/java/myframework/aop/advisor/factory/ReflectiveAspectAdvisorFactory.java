package myframework.aop.advisor.factory;

import myframework.aop.advice.Advice;
import myframework.aop.advisor.Advisor;
import myframework.aop.anntations.AfterAdvice;
import myframework.aop.anntations.Aspect;
import myframework.aop.factory.AspectInstanceFactory;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Robin
 * @date 2019/12/2 -20:56
 */
public class ReflectiveAspectAdvisorFactory implements AspectAdvisorFactory
{


    @Override
    public List<Advisor> getAdvisors(AspectInstanceFactory aif)
    {
        for (Method candidateAdviceMethod : aif.getAspectMetaData().getAdviceMethodInfoMap().keySet())
        {
            Advisor advisor = getAdvisor(candidateAdviceMethod, aif);
        }
        return null;
    }

    private AspectExpressionPointcut getPointCut(Method candidateAdviceMethod, AspectInstanceFactory aif)
    {
        Map<Method, Annotation> adviceMethodMap = aif.getAspectMetaData().getAdviceMethodInfoMap();
        Annotation aspectAnnotation = adviceMethodMap.get(candidateAdviceMethod);
        if(aspectAnnotation instanceof AfterAdvice)
        {
            ((AfterAdvice) aspectAnnotation).value();
        }

    }
    @Override
    public Advisor getAdvisor(Method candidateAdviceMethod, AspectInstanceFactory aif)
    {
        /**
         * 此处的逻辑是用candidateAdviceMethod从aspectMetaData中获取definition,然后获取注解pointcut,
         * 再生成一个PointCutAdvisor
         */




        return null;
    }


    @Override
    public boolean isAspect(Class<?> beanType)
    {
        return beanType.getAnnotation(Aspect.class) != null;
    }


//    @Override
//    public boolean isAspect(Object bean)
//    {
//        return;
//    }

    @Override
    public Advice getAdvice()
    {
        return null;
    }
}
