package myframework.core.definition.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 *
 * 使用不同的beanDefinition从而判断哪个bean是切面或者是普通bean,注意此definition非spring的definition
 * ,此处只是用来存放类信息的
 * @author Robin
 * @date 2019/11/30 -21:39
 */
public class AspectBeanDefinition extends BeanDefinition
{



    public AspectBeanDefinition( Class beanClass, String beanName)
    {
        super( beanClass, beanName);

    }



}
