package myframework.aop.advisor.factory;

import myframework.aop.advice.Advice;
import myframework.aop.advisor.Advisor;
import myframework.aop.anntations.AfterAdvice;
import myframework.aop.anntations.Aspect;
import myframework.aop.factory.AspectInstanceFactory;
import myframework.aop.metadata.AspectMetaData;
import myframework.aop.pointcut.PointCut;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
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

    private AspectExpressionPointcut getPointCut(Method candidateAdviceMethod, AspectMetaData amd)
    {
        Map<Method, Annotation> adviceMethodMap = amd.getAdviceMethodInfoMap();
        Annotation aspectAnnotation = adviceMethodMap.get(candidateAdviceMethod);
        String attributeName = "value";
        try
        {
            Method annotationValue = aspectAnnotation.annotationType().getDeclaredMethod(attributeName);
            String expression=(String)annotationValue.invoke(aspectAnnotation);
            AspectExpressionPointcut aep=new AspectExpressionPointcut(expression,amd.getAspectClass());
            return aep;

        } catch (NoSuchMethodException e)
        {
            return null;
        } catch (InvocationTargetException e)
        {
            throw new IllegalStateException(
                    "Could not obtain value for annotation attribute '" + attributeName + "' in " + aspectAnnotation, e);
        } catch (IllegalAccessException e)
        {
            //通常为反射方法是private所致
            return null;
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
