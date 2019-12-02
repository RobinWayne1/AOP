package myframework.aop.advisor.factory;

import myframework.aop.advice.Advice;
import myframework.aop.advisor.Advisor;
import myframework.aop.factory.AspectInstanceFactory;
import myframework.core.definition.impl.AspectBeanDefinition;
import myframework.core.factory.ConfigurableInstantiationCapableBeanFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 两头传beanFactory有点蠢,但是因为
 *
 * @author Robin
 * @date 2019/12/2 -20:56
 */
public class ReflectiveAspectAdvisorFactory implements AspectAdvisorFactory
{
    private final ConfigurableInstantiationCapableBeanFactory beanFactory;

    public ReflectiveAspectAdvisorFactory(ConfigurableInstantiationCapableBeanFactory beanFactory)
    {
        this.beanFactory = beanFactory;
    }

    @Override
    public List<Advisor> getAdvisors(AspectInstanceFactory aif)
    {
        return null;
    }

    @Override
    public Advisor getAdvisor(Method candidateAdviceMethod, AspectInstanceFactory aif)
    {
        /**
         * 此处的逻辑是用candidateAdviceMethod从map中获取definition,然后获取注解pointcut,
         * 再生成一个PointCutAdvisor
         *
         */



        return null;
    }

    //导致type和beanDefinition耦合在一起了,实在是烂爆的设计
    @Override
    public boolean isAspect(String beanName)
    {
        return this.beanFactory.getBeanDefinitionMap().get(beanName) instanceof AspectBeanDefinition;
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
