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
 * @author Robin
 * @date 2019/11/28 -15:48
 */
public class AnnotationAwareAutoProxyCreator extends AbstractAdvisorAutoProxyCreator
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
     * 默认为空方法,留给子类扩展,主要用于某些子类creator
     * 需要某些advisor时在此处创建并加入advisor链
     */
    @Override
    protected void extendAdvisor(List<Advisor>advisorList)
    {
        advisorList.add(new DefaultPointcutAdvisor(ExposeInvocationInterceptor.INSATNCE));
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
