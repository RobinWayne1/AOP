package myframework.aop.framework;


import myframework.aop.advice.impl.ExposeInvocationInterceptor;
import myframework.aop.advisor.Advisor;
import myframework.aop.advisor.factory.AspectAdvisorFactory;
import myframework.aop.advisor.factory.ReflectiveAspectAdvisorFactory;
import myframework.aop.advisor.impl.DefaultPointcutAdvisor;
import myframework.aop.advisor.impl.InstantiationModelAwarePointcutAdvisorImpl;
import myframework.core.factory.ConfigurableInstantiationCapableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类的主要功能是增加基于注解配置的功能
 *
 * 在Spring中，此类将会在loadBeanDefinition()时,
 * 通过实现了{@link BeanDefinitionRegistry}的{@link DefaultListableBeanFactory}类的registerBeanDefinition()方法
 * ,将{@link AnnotationAwareAspectJAutoProxyCreator}的BeanDefinition注册进{@link DefaultListableBeanFactory}的
 * BeanDefinitionMap中,使其也成为一个bean.若使用的是ApplicationContext类refresh()容器,则会在
 * 刷新过程中通过registerBeanPostProcessors()方法中扫描definitionMap获取到所有是BeanPostProcessor.class的bean,
 * 将其放入{@link AbstractBeanFactory}的beanPostProcessors成员变量中
 * @author Robin
 * @date 2019/11/28 -15:48
 */
public class AnnotationAwareAutoProxyCreator extends AbstractAspectAwareAdvisorAutoProxyCreator
{

    private BeanFactoryAspectAdvisorBuilder builder;

    private AspectAdvisorFactory advisorFactory;

    /**
     * 将beanFactory传给bean
     *
     * @param beanFactory
     */
    @Override
    public void initBeanFactory(ConfigurableInstantiationCapableBeanFactory beanFactory)
    {
        this.advisorFactory=new ReflectiveAspectAdvisorFactory();

        this.builder=new BeanFactoryAspectAdvisorBuilder(beanFactory,this.advisorFactory);
    }



    /**
     * 找到候选增强器
     *
     * @return
     */
    @Override
    protected List<Advisor> findCandidateAdvisors()
    {
        List<Advisor> advisors =new ArrayList<>();
        //super.findCandidateAdvisors();
        advisors.addAll(this.builder.buildAspectAdvisor());
        return advisors;
    }
}
