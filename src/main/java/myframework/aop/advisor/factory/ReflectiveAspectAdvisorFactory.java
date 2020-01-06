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

import myframework.aop.metadata.AspectMetadata;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;
import myframework.exception.NotAnAspectException;
import myframework.exception.UnknowedOperationException;
import myframework.util.AnnotationUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
     * @param aif
     * @return
     */
    @Override
    public List<Advisor> getAdvisors(MetaDataAwareAspectInstanceFactory aif)
    {
        Class<?> aspectClass = aif.getAspectMetaData().getAspectClass();
        validate(aspectClass);
        List<Advisor> candidateAdvisor = new ArrayList<>();

        for (Method candidateAdviceMethod : getAdvisorMethods(aspectClass))
        {
            Advisor advisor = getAdvisor(candidateAdviceMethod, aif, candidateAdvisor.size());
            if (advisor != null)
            {
                candidateAdvisor.add(advisor);
            }
        }

        return candidateAdvisor;
    }

    /**
     * spring在这个方法里涉及增强的初步排序,没有看懂这个排序的意义
     *
     * @param aspectClass
     * @return
     */
    private List<Method> getAdvisorMethods(Class<?> aspectClass)
    {
        return new ArrayList<>(Arrays.asList(aspectClass.getDeclaredMethods()));
    }

    /**
     * AspectMetaDataFactory只过滤了没有注解的切面方法,这里还要过滤一遍有注解但是非AOP注解的方法,但是
     * 此处方法只控制了如果注解不含value()时返回null,或者是含value()不符合格式报错,并没有检测是否是AOP注解
     * <p>
     * AspectMetaDataFactory过滤了AOP注解,所以在AspectMetaData的map中必定是正确的AOP注解,楼上有误
     * <p>
     * AspectMetadataFactory已经舍弃不用了,现在用findAspectJAnnotationOnMethod(candidateAdviceMethod)得到方法上的注解
     *
     * @param candidateAdviceMethod
     * @param candidateAspectClass
     * @return
     */
    private AspectExpressionPointcut getPointCut(Method candidateAdviceMethod, Class<?> candidateAspectClass)
    {
        /**
         * spring这里是在抽象advisor工厂中用findAspectJAnnotationOnMethod()得到方法的注解
         *
         */
        Annotation aspectAnnotation = findAspectJAnnotationOnMethod(candidateAdviceMethod);
        /**
         * 下面几行应该可以放进AspectAnnotation,涉及到的类本应有AnnotationUtil和AspectAnnotation,
         * 后者 存放expression和通过AnnotationUtil获得annotation上的value值
         */

        String attributeName = "value";
        String expression = (String) AnnotationUtil.findAnnotationValue(attributeName, aspectAnnotation);
        if(expression!=null)
        {
            AspectExpressionPointcut aep = new AspectExpressionPointcut(candidateAspectClass);
            aep.setExpression(expression);
            return aep;
        }
        return null;

    }

    /**
     * @param candidateAdviceMethod
     * @param aif
     * @return
     */
    @Override
    public Advisor getAdvisor(Method candidateAdviceMethod, MetaDataAwareAspectInstanceFactory aif, int declaringOrder)
    {
        validate(aif.getAspectMetaData().getAspectClass());
        /**
         * 此处的逻辑是用candidateAdviceMethod从aspectMetaData中获取definition,然后获取注解pointcut,
         * 再生成一个PointCutAdvisor
         * 1、先生成切点
         * 2、根据切点生成Advisor,里面由生成advice
         */
        AspectExpressionPointcut pc = getPointCut(candidateAdviceMethod, aif.getAspectMetaData().getAspectClass());
        //方法没有增强注释时,则不是增强
        if (pc == null)
        {
            return null;
        }
        //要构建advice,首先要知道advice类型,即需要aif
        return new InstantiationModelAwarePointcutAdvisorImpl(pc, this, aif, candidateAdviceMethod, aif.getAspectMetaData().getAspectName(), declaringOrder);

    }


    @Override
    public boolean isAspect(Class<?> beanType)
    {
        return beanType.getAnnotation(Aspect.class) != null;
    }


    /**
     * <p>1.得到AOP注解</p>
     * <p>2.根据AOP注解类型生成Advice</p>
     * <p>3.调用Advice的calculateArgumentBindings()方法,判断是否绑定JoinpPoint(设置一个flag),然后绑定其他参数如
     * afterReturnin的参数</p>
     *
     * @param candidateAdviceMethod
     * @param aexp
     * @param aif
     * @return
     */
    @Override
    public Advice getAdvice(Method candidateAdviceMethod, AspectExpressionPointcut aexp, MetaDataAwareAspectInstanceFactory aif, int declaringOrder)
    {
        validate(aif.getAspectMetaData().getAspectClass());
        //不知为何spring的advice有了AspectMetaData还要传入AspectName

        Annotation methodAnnotation = findAspectJAnnotationOnMethod(candidateAdviceMethod);
        AbstractAspectAdvice aopAdvice;
        if (methodAnnotation instanceof After)
        {
            aopAdvice = new AfterAdvice(candidateAdviceMethod, aexp, aif, declaringOrder);
        } else if (methodAnnotation instanceof Before)
        {
            aopAdvice = new BeforeAdvice(candidateAdviceMethod, aexp, aif, declaringOrder);
        } else if (methodAnnotation instanceof Around)
        {
            aopAdvice = new AroundAdvice(candidateAdviceMethod, aexp, aif, declaringOrder);
        } else
        {
            throw new UnknowedOperationException("Unsupported advice type on method" + candidateAdviceMethod);
        }
        aopAdvice.setAspectName(aif.getAspectMetaData().getAspectName());

        aopAdvice.calculateArgumentBindings();

        return aopAdvice;


    }
}
