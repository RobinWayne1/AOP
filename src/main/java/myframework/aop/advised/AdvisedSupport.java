package myframework.aop.advised;

import com.sun.istack.internal.Nullable;
import myframework.aop.advice.MethodInterceptor;
import myframework.aop.advisor.Advisor;
import myframework.aop.factory.AdvisorChainFactory;
import myframework.aop.factory.impl.DefaultAdvisorChainFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 给ReflectiveMethodInvocation使用的config,将在容器加载时解析好的所有切面方法执行
 * 相关成员信息放到此处
 *
 * @author Robin
 * @date 2019/12/9 -18:56
 */
public class AdvisedSupport implements Advised
{

    private Object target;

    private final AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();

    private List<Advisor> advisors = new ArrayList<>();

    private List<Class<?>> interfaces = new ArrayList<>();

    /**
     * 是否已经先以目标类为精度过滤了一次
     */
    private boolean isPreFiltered;
    /**
     * 在chainFactory中解析过一次之后就放入此处
     */
    private Map<Method, List<MethodInterceptor>> methodCache;

    public List<MethodInterceptor> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass)
    {
        List<MethodInterceptor> interceptors;
        if (methodCache.get(method) == null)
        {
            interceptors = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(this, method, targetClass);
            methodCache.put(method,interceptors);
            return interceptors;
        }
        return methodCache.get(method);
    }

    @Override
    public void addAdvisor(Advisor advisor)
    {
        this.advisors.add(advisor);
    }

    @Override
    public boolean isPreFiltered()
    {
        return isPreFiltered;
    }

    @Override
    public void setPreFiltered(boolean preFiltered)
    {
        isPreFiltered = preFiltered;
    }

    @Override
    public Advisor[] getAdvisors()
    {
        return this.advisors.toArray(new Advisor[this.advisors.size()]);
    }

    public Object getTarget()
    {
        return target;
    }

    public void setTarget(Object target)
    {
        this.target = target;
    }

    public AdvisorChainFactory getAdvisorChainFactory()
    {
        return advisorChainFactory;
    }

    public void addAdvisors(Advisor advisors)
    {
        this.advisors.add(advisors);
    }

    public Map<Method, List<MethodInterceptor>> getMethodCache()
    {
        return methodCache;
    }

    public void setMethodCache(Map<Method, List<MethodInterceptor>> methodCache)
    {
        this.methodCache = methodCache;
    }

    public List<Class<?>> getInterfaces()
    {
        return interfaces;
    }

    public boolean removeInterface(Class<?> intf)
    {
        return this.interfaces.remove(intf);
    }

    public void addInterfaces(Class<?> intf)
    {
        this.interfaces.add(intf);
    }

    public void setInterfaces(List<Class<?>> interfaces)
    {
        this.interfaces.clear();
        this.interfaces = interfaces;
    }
}
