package myframework.aop.factory;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Robin
 * @date 2019/12/7 -20:32
 */
public interface AdvisorChainFactory
{
    List<Object> getInterceptorsAndDynamicInterceptionAdvice(Advised config, Method method, @Nullable Class<?> targetClass);
}
