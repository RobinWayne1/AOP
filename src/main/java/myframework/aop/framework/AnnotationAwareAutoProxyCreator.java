package myframework.aop.framework;

import myframework.aop.advice.AspectAdvice;
import myframework.aop.advisor.factory.AspectAdvisorFactory;
import myframework.aop.advisor.factory.ReflectiveAspectAdvisorFactory;
import myframework.core.aware.BeanFactoryAware;
import myframework.core.factory.BeanFactory;
import myframework.core.factory.ConfigurableInstantiationCapableBeanFactory;
import myframework.core.processor.BeanPostProcessor;

/**
 * @author Robin
 * @date 2019/11/28 -15:48
 */
public class AnnotationAwareAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware
{

    private ConfigurableInstantiationCapableBeanFactory beanFactory;

    private BeanFactoryAspectAdvisorBuilder builder;

    private AspectAdvisorFactory advisorFactory;
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
        return null;
    }

    /**
     * 将beanFactory传给bean
     *
     * @param beanFactory
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory)
    {
        this.beanFactory=(ConfigurableInstantiationCapableBeanFactory)beanFactory;

        this.advisorFactory=new ReflectiveAspectAdvisorFactory();

        this.builder=new BeanFactoryAspectAdvisorBuilder(this.beanFactory,this.advisorFactory);

    }

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
