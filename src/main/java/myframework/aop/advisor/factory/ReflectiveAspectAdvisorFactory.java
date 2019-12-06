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

    /**
     * AspectMetaDataFactory只过滤了没有注解的切面方法,这里还要过滤一遍有注解但是非AOP注解的方法,但是
     * 此处方法只控制了如果注解不含value()时返回null,或者是含value()不符合格式报错,并没有检测是否是AOP注解
     * @param candidateAdviceMethod
     * @param amd
     * @return
     */
    private AspectExpressionPointcut getPointCut(Method candidateAdviceMethod, AspectMetaData amd)
    {
        Map<Method, Annotation> adviceMethodMap = amd.getAdviceMethodInfoMap();
        Annotation aspectAnnotation = adviceMethodMap.get(candidateAdviceMethod);
        String attributeName = "value";
        try
        {
            //即得到注解的value()定义方法
            Method annotationValue = aspectAnnotation.annotationType().getDeclaredMethod(attributeName);
            //然后invoke这个注解定义方法得到注解目标方法上的value值
            String expression=(String)annotationValue.invoke(aspectAnnotation);
            AspectExpressionPointcut aep=new AspectExpressionPointcut(amd.getAspectClass());
            aep.setExpression(expression);
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

    /**
     *
     * @param candidateAdviceMethod
     * @param aif
     * @return
     */
    @Override
    public Advisor getAdvisor(Method candidateAdviceMethod, AspectInstanceFactory aif)
    {
        /**
         * 此处的逻辑是用candidateAdviceMethod从aspectMetaData中获取definition,然后获取注解pointcut,
         * 再生成一个PointCutAdvisor
         * 1、先生成切点
         * 2、根据切点生成Advisor
         */
        AspectExpressionPointcut pc=getPointCut(candidateAdviceMethod,aif.getAspectMetaData());
        if(pc==null)
        {
            return null;
        }
        //要构建advice,首先要知道advice类型,即需要aif

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
