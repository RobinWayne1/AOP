package myframework.aop.framework;

import myframework.aop.advisor.Advisor;
import myframework.aop.advisor.factory.AspectAdvisorFactory;
import myframework.aop.factory.AspectInstanceFactory;
import myframework.aop.factory.impl.NonLazyInitAspectInstanceFactory;
import myframework.core.definition.Definition;
import myframework.core.definition.impl.AspectBeanDefinition;
import myframework.core.factory.ConfigurableInstantiationCapableBeanFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * For building the advisor
 *
 * @author Robin
 * @date 2019/12/1 -19:58
 */
public class BeanFactoryAspectAdvisorBuilder
{

    private final ConfigurableInstantiationCapableBeanFactory beanFactory;

    private final AspectAdvisorFactory advisorFactory;

    private List<String> aspectBeanNames;

    public BeanFactoryAspectAdvisorBuilder(ConfigurableInstantiationCapableBeanFactory beanFactory, AspectAdvisorFactory advisorFactory)
    {
        this.beanFactory = beanFactory;
        this.advisorFactory = advisorFactory;
    }



    public synchronized List<Advisor> buildAspectAdvisor()
    {
        Set<String> beanNames = this.beanFactory.getBeanName();
        //先从advisorFactory判断是否为aspect,如果是则从aspectFactory得到aspect,再去advisorFactory得到advisors
        for(String beanName:beanNames)
        {
            if(this.advisorFactory.isAspect(beanName))
            {
                AspectInstanceFactory aif=new NonLazyInitAspectInstanceFactory(beanName,beanFactory);
                List<Advisor> advisors=this.advisorFactory.getAdvisors(aif);
            }
        }
    }

}
