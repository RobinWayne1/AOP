package myframework.aop.advisor.factory;

import myframework.aop.advice.AbstractAspectAdvice;
import myframework.aop.advice.Advice;
import myframework.aop.advice.impl.AfterAdvice;
import myframework.aop.advice.impl.AroundAdvice;
import myframework.aop.advice.impl.BeforeAdvice;
import myframework.aop.advisor.Advisor;
import myframework.aop.advisor.impl.InstantiationModelAwarePointcutAdvisorImpl;

import myframework.aop.anntations.After;
import myframework.aop.anntations.Around;
import myframework.aop.anntations.Aspect;
import myframework.aop.anntations.Before;
import myframework.aop.factory.MetaDataAwareAspectInstanceFactory;
import myframework.aop.metadata.AspectMetaData;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;
import myframework.exception.NotAnAspectException;
import myframework.exception.UnknowedOperationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robin
 * @date 2019/12/2 -20:56
 */
public class ReflectiveAspectAdvisorFactory extends AbstractAdvisorFactory
{
    protected void validate(Class<?> cl)
    {
        if (!isAspect(cl))
        {
            throw new NotAnAspectException(cl);
        }
    }


    /**
     * 考虑一下到底要不要将aspectMetadata封装成内部类,每次都做切面判断实在太过麻烦
     * 但是这样也增加了可扩展性
     *
     * @param aif
     * @return
     */
    @Override
    public List<Advisor> getAdvisors(MetaDataAwareAspectInstanceFactory aif)
    {

        validate(aif.getAspectMetaData().getAspectClass());
        List<Advisor> candidateAdvisor = new ArrayList<>();

        for (Method candidateAdviceMethod : aif.getAspectMetaData().getAdviceMethodInfoMap().keySet())
        {
            candidateAdvisor.add(getAdvisor(candidateAdviceMethod, aif));
        }

        return candidateAdvisor;
    }

    /**
     * AspectMetaDataFactory只过滤了没有注解的切面方法,这里还要过滤一遍有注解但是非AOP注解的方法,但是
     * 此处方法只控制了如果注解不含value()时返回null,或者是含value()不符合格式报错,并没有检测是否是AOP注解
     * <p>
     * AspectMetaDataFactory过滤了AOP注解,所以在AspectMetaData的map中必定是正确的AOP注解,楼上有误
     *
     * @param candidateAdviceMethod
     * @param amd
     * @return
     */
    private AspectExpressionPointcut getPointCut(Method candidateAdviceMethod, AspectMetaData amd)
    {
        Map<Method, Annotation> adviceMethodMap = amd.getAdviceMethodInfoMap();
        Annotation aspectAnnotation = adviceMethodMap.get(candidateAdviceMethod);
        //下面几行应该可以放进abstract
        String attributeName = "value";
        try
        {
            //即得到注解的value()定义方法
            Method annotationValue = aspectAnnotation.annotationType().getDeclaredMethod(attributeName);
            //然后invoke这个注解定义方法得到注解目标方法上的value值
            String expression = (String) annotationValue.invoke(aspectAnnotation);
            AspectExpressionPointcut aep = new AspectExpressionPointcut(amd.getAspectClass());
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
     * @param candidateAdviceMethod
     * @param aif
     * @return
     */
    @Override
    public Advisor getAdvisor(Method candidateAdviceMethod, MetaDataAwareAspectInstanceFactory aif)
    {
        validate(aif.getAspectMetaData().getAspectClass());
        /**
         * 此处的逻辑是用candidateAdviceMethod从aspectMetaData中获取definition,然后获取注解pointcut,
         * 再生成一个PointCutAdvisor
         * 1、先生成切点
         * 2、根据切点生成Advisor,里面由生成advice
         */
        AspectExpressionPointcut pc = getPointCut(candidateAdviceMethod, aif.getAspectMetaData());
        if (pc == null)
        {
            return null;
        }
        //要构建advice,首先要知道advice类型,即需要aif
        return new InstantiationModelAwarePointcutAdvisorImpl(pc, this, aif, candidateAdviceMethod, aif.getAspectMetaData().getAspectName());

    }


    @Override
    public boolean isAspect(Class<?> beanType)
    {
        return beanType.getAnnotation(Aspect.class) != null;
    }


    @Override
    public Advice getAdvice(Method candidateAdviceMethod, AspectExpressionPointcut aexp, MetaDataAwareAspectInstanceFactory aif)
    {
        validate(aif.getAspectMetaData().getAspectClass());
        //不知为何spring的advice有了AspectMetaData还要传入AspectName

        Annotation methodAnnotation = aif.getAspectMetaData().getAdviceMethodInfoMap().get(candidateAdviceMethod);
        AbstractAspectAdvice aopAdvice;
        if (methodAnnotation instanceof After)
        {
            aopAdvice = new AfterAdvice(candidateAdviceMethod, aexp, aif);
        } else if (methodAnnotation instanceof Before)
        {
            aopAdvice = new BeforeAdvice(candidateAdviceMethod, aexp, aif);
        } else if (methodAnnotation instanceof Around)
        {
            aopAdvice = new AroundAdvice(candidateAdviceMethod, aexp, aif);
        } else
        {
            throw new UnknowedOperationException("Unsupported advice type on method" + candidateAdviceMethod);
        }
        aopAdvice.setAspectName(aif.getAspectMetaData().getAspectName());

        aopAdvice.calculateArgumentBindings();

        return aopAdvice;


    }
}
