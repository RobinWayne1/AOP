package myframework.aop.framework;

import myframework.aop.advised.ProxyFactory;
import myframework.aop.advisor.Advisor;
import myframework.core.aware.BeanFactoryAware;
import myframework.core.factory.BeanFactory;
import myframework.core.factory.ConfigurableInstantiationCapableBeanFactory;
import myframework.core.processor.BeanPostProcessor;
import myframework.exception.UnknowedOperationException;
import myframework.util.AopUtil;

import java.util.List;

/**
 * 由于我将properties的auto-proxy当成是基于注解开发,而且目前也只有这一种使用AOP的方法,所以
 * 此类中很多不是基于注解开发的方法都还没有完成,只能声明为抽象方法让子类实现
 *
 * @author Robin
 * @date 2019/12/8 -10:19
 */
public abstract class AbstractAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware
{

    private ConfigurableInstantiationCapableBeanFactory beanFactory;

    protected static final Object[] DO_NOT_PROXY = null;

    /**
     * 后置后处理器
     *
     * @param bean
     * @param beanName
     * @return
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
    {
        /**
         * 得到所有advisor,然后在所有bean中根据pointcut过滤一部分不需要的advisor
         * ,然后创建代理
         */
        if (bean != null)
        {
            return wrapIfNecessary(bean, beanName);

        }
        return bean;
    }

    protected Object createProxy(Object target, Object[] specificInterceptors)
    {
        /**
         * 创建ProxyFactory,配置ProxyFactory,创建Proxy
         */
        ProxyFactory proxyFactory = new ProxyFactory(target);
        /**
         * 已经事先以当前要代理的bean为对象过滤了一遍advisor
         */
        proxyFactory.setPreFiltered(true);

        Advisor[] advisors = buildAdvisors(specificInterceptors);

        for (Advisor advisor : advisors)
        {
            proxyFactory.addAdvisor(advisor);
        }
        return proxyFactory.getProxy();
    }

    /**
     * 留作可能用到adapter的扩展
     *
     * @param specificInterceptors
     * @return
     */
    protected Advisor[] buildAdvisors(Object[] specificInterceptors)
    {
        Advisor[] advisors = new Advisor[specificInterceptors.length];
        for (int i = 0; i < specificInterceptors.length; i++)
        {
            if (specificInterceptors[i] instanceof Advisor)
            {
                advisors[i] = (Advisor) specificInterceptors[i];
            } else
            {
                throw new UnknowedOperationException("Unknown type advisor");
            }
        }
        return advisors;
    }

    protected Object wrapIfNecessary(Object bean, String beanName)
    {
        //为该bean找到合适的advisor,如果一个都没找到就为DO_NOT_PROXY
        Object[] specificInterceptors = getAdviceAndAdvisorsForBean(bean.getClass(), beanName);
        if (specificInterceptors == DO_NOT_PROXY)
        {
            return bean;
        }
        /**
         * 由于纯单例模式,所以不会出现二次创建同一个bean的情况,所以不需要cache
         */
        return createProxy(bean,specificInterceptors);

    }

    protected List<Advisor> sortAdvisors(List<Advisor> advisors)
    {

    }

    /**
     * 也算是个模板方法模式
     *
     * @param beanClass
     * @param beanName
     * @return
     */
    protected Object[] getAdviceAndAdvisorsForBean(Class<?> beanClass, String beanName)
    {
        List<Advisor> candidateAdvisors = findCandidateAdvisors();
        List<Advisor> eligibleAdvisors = AopUtil.findAdvisorsThatCanApply(candidateAdvisors, beanClass);

        if (eligibleAdvisors.isEmpty())
        {
            return DO_NOT_PROXY;
        }
        extendAdvisor();
        eligibleAdvisors = sortAdvisors(eligibleAdvisors);
        return eligibleAdvisors.toArray();
    }

    /**
     * 用子类实现的方法
     *
     * @return
     */
    protected abstract List<Advisor> findCandidateAdvisors();

    /**
     * 将beanFactory传给bean
     *
     * @param beanFactory
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory)
    {
        if (!(beanFactory instanceof ConfigurableInstantiationCapableBeanFactory))
        {
            throw new IllegalArgumentException(
                    "AdvisorAutoProxyCreator requires a ConfigurableListableBeanFactory: " + beanFactory);
        }
        this.beanFactory = (ConfigurableInstantiationCapableBeanFactory) beanFactory;
        initBeanFactory(this.beanFactory);


    }

    /**
     * 初始化所需要的工厂
     *
     * @param beanFactory
     */
    public abstract void initBeanFactory(ConfigurableInstantiationCapableBeanFactory beanFactory);
}
