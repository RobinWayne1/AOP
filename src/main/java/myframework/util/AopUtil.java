package myframework.util;

import myframework.aop.advisor.Advisor;
import myframework.aop.advisor.PointcutAdvisor;
import myframework.aop.pointcut.ClassFilter;
import myframework.aop.pointcut.MethodMatcher;
import myframework.aop.pointcut.PointCut;
import myframework.exception.AopInvocationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Jdk动态代理是基于接口的代理,只能代理接口有的方法.并且newInstance时只能将Object强制转换为接口,不能转换为
 * 具体实现类
 *
 * @author Robin
 * @date 2019/12/8 -17:03
 */
public class AopUtil
{

    public static Object invokeJoinpointUsingReflection(Object target, Method method, Object[] args) throws Throwable
    {
        try
        {
            //取消修饰符安全检查
            method.setAccessible(true);
            return method.invoke(target, args);
        } catch (InvocationTargetException ex)
        {
            // Invoked method threw a checked exception.
            // We must rethrow it. The client won't see the interceptor.
            throw ex.getTargetException();
        } catch (IllegalArgumentException ex)
        {
            throw new AopInvocationException("AOP configuration seems to be invalid: tried calling method [" +
                    method + "] on target [" + target + "]", ex);
        } catch (IllegalAccessException ex)
        {
            throw new AopInvocationException("Could not access method [" + method + "]", ex);
        }

    }

    /**
     * 思路:判断参数中的增强List有没有符合该bean的,先用classFilter,后用methodMatcher
     */
    public static List<Advisor> findAdvisorsThatCanApply(List<Advisor> candidateAdvisors, Class<?> beanClass)
    {
        List<Advisor> eligibleAdvisors = new LinkedList<>();
        for (Advisor candidateAdvisor : candidateAdvisors)
        {
            if (candidateAdvisor instanceof PointcutAdvisor)
            {
                PointcutAdvisor pa = (PointcutAdvisor) candidateAdvisor;
                PointCut pc = pa.getPointcut();
                ClassFilter classFilter = pc.getClassFilter();
                MethodMatcher methodMatcher = pc.getMethodMatcher();
                if (!classFilter.matches(beanClass))
                {
                    return Collections.emptyList();
                }
                /**
                 * 因为实现类可以在实现接口的前提下扩展接口,而Jdk动态代理是不予代理这种扩展的,所以必须从pointcut
                 * 指向的实现类的接口入手
                 */

                for (Class<?> interfaceClass : beanClass.getInterfaces())
                {
                    for (Method interfaceMethod : interfaceClass.getMethods())
                    {
                        if (methodMatcher.matches(interfaceMethod))
                        {
                            eligibleAdvisors.add(pa);
                        }
                    }
                }
            }
        }
        return eligibleAdvisors;
    }
}
