package myframework.servicefactory.definition.impl;

import myframework.aop.pointcut.Pointcut;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 使用不同的beanDefinition从而判断哪个bean是切面或者是普通bean,注意此definition非spring的definition
 * ,此处只是用来存放类信息的
 * @author Robin
 * @date 2019/11/30 -21:39
 */
public class AspectBeanDefinition extends BeanDefinition
{
//    public AspectBeanDefinition(Set<Method> methods, Class beanClass, String beanName)
//    {
//        super(methods,beanClass,beanName);
//    }

    //另一个思路是buildAdvisor时再createPointcut
    private final Map<Method, Annotation[]> adviceMethodInfoMap;



    public AspectBeanDefinition(List<Method> methods, Class beanClass, String beanName, Map<Method, Annotation[]> adviceMethodInfoMap)
    {
        super(methods, beanClass, beanName);
        this.adviceMethodInfoMap=adviceMethodInfoMap;

    }

//    private void createPointcut()
//    {
//        for (Annotation annotation : annotations)
//        {
//
//        }
//    }

}
