package myframework.core.factory.impl;


import myframework.core.factory.*;
import myframework.core.processor.BeanPostProcessor;
import myframework.core.reader.DefinitionReader;
import myframework.core.reader.impl.AspectConfigDefinitionReader;
import myframework.core.reader.impl.PropertiesDefinitionReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 自定义beanDefinition读取
 *
 * @author Robin
 * @date 2019/11/29 -12:33
 */
public class PropertiesApplicationContext implements WebApplicationContext
{


    private final ConfigurableInstantiationCapableBeanFactory beanFactory;

//    public WebApplicationContext(String configPath)
//    {
//        serviceFactory=new DefaultServiceFactory(configPath);
//    }

    public PropertiesApplicationContext()
    {
        List<DefinitionReader> readers = new ArrayList<>(2);

        readers.add(new PropertiesDefinitionReader());
        readers.add(new AspectConfigDefinitionReader());

        this.beanFactory = new DefaultBeanFactory(readers);

        registerPostProcessor();

        createAllBeanInstance();
    }

    private void createAllBeanInstance()
    {
        Set<String> beanNames = getBeanName();

        for (String beanName : beanNames)
        {
            this.beanFactory.createBean(beanName);
        }
    }

    protected void registerPostProcessor()
    {
        List<String> beanNames = this.beanFactory.getBeanNamesForType(BeanPostProcessor.class);
        for (String beanName : beanNames)
        {
            BeanPostProcessor beanPostProcessor = (BeanPostProcessor) getBean(beanName);
            this.beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * 返回bean信息
     *
     * @return
     */
    @Override
    public Set<String> getBeanName()
    {
        return this.beanFactory.getBeanName();
    }

    /**
     * 从ObjectFactory中返回指定bean
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName)
    {
        return this.beanFactory.getBean(beanName);
    }
}
