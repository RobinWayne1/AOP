package myframework.servicefactory.factory.impl;

import myframework.exception.BeanInstantiateException;
import myframework.servicefactory.aware.Aware;
import myframework.servicefactory.aware.BeanFactoryAware;
import myframework.servicefactory.aware.BeanNameAware;
import myframework.servicefactory.factory.AutowireCapableBeanFactory;
import myframework.servicefactory.factory.BeanFactory;
import myframework.servicefactory.definition.Definition;
import myframework.servicefactory.factory.ConfigurableBeanFactory;
import myframework.servicefactory.processor.BeanPostProcessor;
import myframework.servicefactory.reader.DefinitionReader;
import myframework.servicefactory.reader.impl.PropertiesDefinitionReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Robin
 * @date 2019/11/29 -17:01
 */
public class DefaultBeanFactory implements ConfigurableBeanFactory, AutowireCapableBeanFactory
{
    /**
     * bean映射
     */
    private final Map<String, Object> beanMap = new ConcurrentHashMap<>(16);

    private final Map<String, Definition> beanDefinitionMap;

    private final List<DefinitionReader> readers;

    private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

    @Override
    public Map<String, Definition> getBeanDefinitionMap()
    {
        return beanDefinitionMap;
    }


    public DefaultBeanFactory(List<DefinitionReader> readers)
    {
        this.readers = readers;
        this.beanDefinitionMap = parse();
    }

    public DefaultBeanFactory()
    {
        readers = new ArrayList<>();
        readers.add(new PropertiesDefinitionReader());
        this.beanDefinitionMap = parse();
    }

    private Map<String, Definition> parse()
    {
        Map<String, Definition> beanDefinitionMap = new ConcurrentHashMap<>();
        //解析Config,装饰
        for (DefinitionReader reader : this.readers)
        {
            reader.loadBeanDefinition(beanDefinitionMap);
        }
        return beanDefinitionMap;

    }

    private void invokeAwareMethod(final String beanName, final Object bean)
    {
        if (bean instanceof Aware)
        {
            if (bean instanceof BeanNameAware)
            {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            if (bean instanceof BeanFactoryAware)
            {
                ((BeanFactoryAware) bean).setBeanFactory(DefaultBeanFactory.this);
            }
        }
    }

    @Override
    public Object initializeBean(Object existingBean, String beanName)
    {
        invokeAwareMethod(beanName, existingBean);
        for (BeanPostProcessor processor : this.beanPostProcessors)
        {
            existingBean = processor.postProcessAfterInitialization(existingBean, beanName);
        }
        return existingBean;
    }

    /**
     * 给factory添加后处理器
     *
     * @param postProcessor
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor)
    {
        this.beanPostProcessors.add(postProcessor);
    }

    //需要改动,看是否要将修改过后的bean实例再次送进beanmap中
    @Override
    public void createBean()
    {
        Set<String> beanNames = getBeanName();

        for (String beanName : beanNames)
        {
            for (BeanPostProcessor processor : this.beanPostProcessors)
            {
                //需要修改,不可以单纯从beanMap.getnull
                processor.postProcessBeforeInitialization(this.beanMap.get(beanName), beanName);
            }
            //创建bean实例
            Object beanInstance = instantiateClass(beanName);
            this.beanMap.put(beanName, initializeBean(beanInstance, beanName));
        }
    }

    @Override
    public Set<String> getBeanName()
    {
        return this.beanDefinitionMap.keySet();
    }

    /**
     * 实例化bean
     */
    private synchronized Object instantiateClass(String beanName)
    {

        Class cl = this.beanDefinitionMap.get(beanName).getBeanClass();
        if (cl != null)
        {
            try
            {
                if (cl.isInterface())
                {
                    throw new BeanInstantiateException(cl, "Specified class is an interface");
                }
                Object beanInstance = cl.newInstance();
                return beanInstance;
            } catch (InstantiationException e)
            {
                throw new BeanInstantiateException(cl, "Is there an abstract class?");
            } catch (IllegalAccessException e)
            {
                throw new BeanInstantiateException(cl, "Is the constructor accessible?Constructor with parameters is not supported at present");
            }
        }
        return null;

    }

    @Override
    public List<String> getBeanNamesForType(Class<?> cl)
    {
        List<String> beanNames = new ArrayList<>(16);
        Set<Map.Entry<String, Definition>> entrySet = this.beanDefinitionMap.entrySet();
        for (Map.Entry<String, Definition> entry : entrySet)
        {
            Definition mapDefinition = entry.getValue();
            Class actualClass = mapDefinition.getBeanClass();
            if (actualClass == cl)
            {
                beanNames.add(entry.getKey());
            }

        }
        return beanNames;
    }

    private Object getSingleton(String beanName)
    {
        Object beanInstance = this.beanMap.get(beanName);
        if (beanInstance == null)
        {
            instantiateClass(beanName);
        }
        return this.beanMap.get(beanName);

    }

    /**
     * 返回指定bean
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName)
    {
        return getSingleton(beanName);
    }
}
