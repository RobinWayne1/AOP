package myframework.aop.framework;

import myframework.aop.advice.impl.ExposeInvocationInterceptor;
import myframework.aop.advisor.Advisor;
import myframework.aop.advisor.impl.DefaultPointcutAdvisor;

import java.util.List;

/**
 * 提供关于advisor的优先级顺序的解决方法
 *
 * @author Robin
 * @date 2020/1/5 -22:34
 */
public abstract class AbstractAspectAwareAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator
{

    /**
     * 只要保证单个切面内的增强顺序是正确的就行,即afterReturning→after→around→before,其他切面的
     * 根据加载顺序直接加入到上一个切面所有增强的后面.
     * 使用@Order的话就会将变成:Order(1):{Order(2)}:Order(1),即为1的切面的增强
     * 会将2的切面的增强包裹起来,自己想象
     *
     * @param advisors
     * @return
     */
    @Override
    protected List<Advisor> sortAdvisors(List<Advisor> advisors)
    {
        return null;
    }

    /**
     * 默认为空方法,留给子类扩展,主要用于某些子类creator
     * 需要某些advisor时在此处创建并加入advisor链
     */
    @Override
    protected void extendAdvisor(List<Advisor>advisorList)
    {
        advisorList.add(new DefaultPointcutAdvisor(ExposeInvocationInterceptor.INSATNCE));
    }
}
