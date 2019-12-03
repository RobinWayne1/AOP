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
import java.util.concurrent.ConcurrentHashMap;

/**
 * For building the advisor
 *
 * Spring中这个builder并不是传统的建造者模式那样创建一个产品的多个部件,也
 * @author Robin
 * @date 2019/12/1 -19:58
 */
public class BeanFactoryAspectAdvisorBuilder
{

    private final ConfigurableInstantiationCapableBeanFactory beanFactory;

    private final AspectAdvisorFactory advisorFactory;

    /**
     * 切面名,包装好的Advisor
     */
    private final Map<String,List<Advisor>>advisorsCache = new ConcurrentHashMap<>(16);

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
            Class<?> beanType=this.beanFactory.getType(beanName);
            if(this.advisorFactory.isAspect(beanType))
            {
                AspectInstanceFactory aif=new NonLazyInitAspectInstanceFactory(beanName,beanFactory,beanType);
                List<Advisor> advisors=this.advisorFactory.getAdvisors(aif);
            }
        }
    }

}
