package myframework.aop.factory;

import com.sun.istack.internal.Nullable;
import myframework.aop.advice.MethodInterceptor;
import myframework.aop.advised.Advised;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Robin
 * @date 2019/12/7 -20:32
 */
public interface AdvisorChainFactory
{
    /**
     * 得到拦截器链
     * @param config
     * @param method
     * @param targetClass
     * @return
     */
    List<MethodInterceptor> getInterceptorsAndDynamicInterceptionAdvice(Advised config, Method method,  Class<?> targetClass);
}
