package myframework.aop.factory.impl;

import myframework.aop.factory.AdvisorChainFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Robin
 * @date 2019/12/7 -20:32
 */
public class DefaultAdvisorChainFactory implements AdvisorChainFactory
{
    @Override
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Advised config, Method method, Class<?> targetClass)
    {
        return null;
    }
}
