package myframework.aop.framework;

import myframework.aop.advisor.Advisor;
import myframework.aop.advisor.factory.AspectAdvisorFactory;
import myframework.aop.factory.MetaDataAwareAspectInstanceFactory;
import myframework.aop.factory.impl.NonLazyInitAspectInstanceFactory;
import myframework.core.factory.ConfigurableInstantiationCapableBeanFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * For building the advisor
 * <p>
 * Spring中这个builder并不是传统的建造者模式那样创建一个产品的多个部件.
 *
 * @author Robin
 * @date 2019/12/1 -19:58
 */
public class BeanFactoryAspectAdvisorBuilder
{

    private final ConfigurableInstantiationCapableBeanFactory beanFactory;

    private final AspectAdvisorFactory advisorFactory;

    /**
     * 切面名,包装好的Advisor,为什么你会觉得只会调用一次后处理器?每个bean生成完后都要调用后处理器
     * 来到这个方法重新解析advisor,如果不用cache,每次doCreateBean()之后都要重新build一次advisor
     */
    private final Map<String, List<Advisor>> advisorsCache = new ConcurrentHashMap<>(16);

    private List<String> aspectBeanNames;

    public BeanFactoryAspectAdvisorBuilder(ConfigurableInstantiationCapableBeanFactory beanFactory, AspectAdvisorFactory advisorFactory)
    {
        this.beanFactory = beanFactory;
        this.advisorFactory = advisorFactory;
    }


    public synchronized List<Advisor> buildAspectAdvisor()
    {
        List<String> aspectBeanNames = this.aspectBeanNames;

        List<Advisor> advisors = new LinkedList<>();
        //第一次执行buildAdvisor
        if (aspectBeanNames == null)
        {
            aspectBeanNames = new ArrayList<>();
            Set<String> beanNames = this.beanFactory.getBeanName();

            //先从advisorFactory判断是否为aspect,如果是则从aspectFactory得到aspect,再去advisorFactory得到advisors
            for (String beanName : beanNames)
            {
                Class<?> beanType = this.beanFactory.getType(beanName);
                if (this.advisorFactory.isAspect(beanType))
                {
                    aspectBeanNames.add(beanName);
                    //创建切面工厂
                    MetaDataAwareAspectInstanceFactory aif = new NonLazyInitAspectInstanceFactory(beanName, beanFactory, beanType);
                    List<Advisor> aspectAdvisors = this.advisorFactory.getAdvisors(aif);
                    advisors.addAll(aspectAdvisors);
                    this.advisorsCache.put(beanName, aspectAdvisors);
                }
            }
            this.aspectBeanNames=aspectBeanNames;
        } else
        {
            for (String aspectBeanName : aspectBeanNames)
            {
                List<Advisor> aspectAdvisor = this.advisorsCache.get(aspectBeanName);
                if (aspectAdvisor != null)
                {
                    advisors.addAll(aspectAdvisor);
                }
            }
        }
        return advisors;
    }

}
