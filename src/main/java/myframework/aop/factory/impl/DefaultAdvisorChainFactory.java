package myframework.aop.factory.impl;

import myframework.aop.advice.MethodInterceptor;
import myframework.aop.advised.Advised;
import myframework.aop.advisor.Advisor;
import myframework.aop.advisor.PointcutAdvisor;
import myframework.aop.factory.AdvisorChainFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Robin
 * @date 2019/12/7 -20:32
 */
public class DefaultAdvisorChainFactory implements AdvisorChainFactory
{
    @Override
    public List<MethodInterceptor> getInterceptorsAndDynamicInterceptionAdvice(Advised config, Method method, Class<?> targetClass)
    {
        /**
         * 1.将所有advisor取出,先进行class过滤,再进行方法静态过滤
         * 2.第一个是ExposedInvocationInterceptor,所以这个是必过的
         * 3.AdvisorAdapterRegistry,用来包装没有实现MethodInterceptor的advice,比如afterReturning和before
         * 	 也就是说我可以直接用advisor取出advice在将其强制转换为MethodInterceptor
         */
        List<MethodInterceptor> interceptorList=new ArrayList<>(config.getAdvisors().length);
        for (Advisor advisor : config.getAdvisors())
        {
            if (advisor instanceof PointcutAdvisor)
            {
                PointcutAdvisor pointcutAdvisor=(PointcutAdvisor) advisor;
                if (config.isPreFiltered() || pointcutAdvisor.getPointcut().getClassFilter().matches(targetClass))
                {
                    if (pointcutAdvisor.getPointcut().getMethodMatcher().matches(method))
                    {
                        //spring在这里使用了AdvisorAdapterRegistry转换没实现MethodInterceptor的advice
                        MethodInterceptor mi = (MethodInterceptor) advisor.getAdvice();
                    }
                }
            }
        }


        return interceptorList;
    }
}
