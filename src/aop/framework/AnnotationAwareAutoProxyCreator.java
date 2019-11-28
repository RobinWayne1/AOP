package aop.framework;

import aop.advice.Advice;

import java.util.List;

/**
 * @author Robin
 * @date 2019/11/28 -15:48
 */
public class AnnotationAwareAutoProxyCreator
{

    public AnnotationAwareAutoProxyCreator(Object bean,String beanName)
    {
        wrapIfNecessary(bean,beanName);
    }

    public void wrapIfNecessary(Object bean,String beanName)
    {
        //先解析,查看是否有方法可代理,如果有则封装成advice



    }

//    public List<Advice> findCandidateAdvisors()
//    {
//
//    }
}
