package myframework.aop.framework;

import myframework.aop.advice.Advice;
import myframework.aop.advice.impl.BeforeAdvice;
import myframework.aop.advisor.Advisor;
import myframework.util.ConfigUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Robin
 * @date 2019/11/28 -15:48
 */
public class AnnotationAwareAutoProxyCreator
{

//    public AnnotationAwareAutoProxyCreator(Object bean,String beanName)
//    {
//
//        wrapIfNecessary(bean,beanName);
//    }
//
//    /**
//     * 解析advice,用advisor包装,此方法放在creator构造器,wrap前
//     */
//    public  List<Advisor> findAdvisors()
//    {
//        try
//        {
//            String[] aspectClasses = ConfigUtil.getProxyClass();
//            for (String className : aspectClasses)
//            {
//                Class aspectClass = Class.forName(className);
//                Method[] method=aspectClass.getDeclaredMethods();
//                for(Method m:method)
//                {
//                    Annotation[]annotations=m.getAnnotations();
//                    for(Annotation annotation:annotations)
//                    {
//                        if(annotation.annotationType().equals(BeforeAdvice.class))
//                        {
//                            //在此之前先验证目标方法的存在性
//
//                            Advice advice=new BeforeAdvice();
//                        }
//                    }
//                }
//            }
//        }
//        catch (ClassNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void wrapIfNecessary(Object bean,String beanName)
//    {
//        //先解析,查看是否有方法可代理,如果有则封装成advice
//
//
//
//    }
//
//    //解析pointcut
//    private Advisor parse()
//    {
//
//    }
//    public List<Advice> findCandidateAdvisors()
//    {
//
//    }
}
