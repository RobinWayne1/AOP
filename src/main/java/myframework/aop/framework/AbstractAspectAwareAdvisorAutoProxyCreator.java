package myframework.aop.framework;

import myframework.aop.AspectPrecedenceComparator;
import myframework.aop.PartialOrder;
import myframework.aop.advice.Advice;
import myframework.aop.advice.impl.ExposeInvocationInterceptor;
import myframework.aop.advisor.Advisor;
import myframework.aop.advisor.impl.DefaultPointcutAdvisor;
import myframework.core.order.Ordered;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 提供关于advisor的优先级顺序的解决方法
 *
 * @author Robin
 * @date 2020/1/5 -22:34
 */
public abstract class AbstractAspectAwareAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator
{

    private static final Comparator<Advisor> DEFAULT_PRECEDENCE_COMPARATOR = new AspectPrecedenceComparator();

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
        List<PartiallyComparableAdvisorHolder> partialComparableList=new ArrayList<>(advisors.size());
        //构造PartiallyComparableAdvisorHolder
        for (Advisor advisor : advisors)
        {
            /**
             * 使用PartialOrder的好处在这里体现出来,每个advisor可以自定义不同的Comparator
             */
            PartiallyComparableAdvisorHolder p =
                    new PartiallyComparableAdvisorHolder(advisor, DEFAULT_PRECEDENCE_COMPARATOR);
            partialComparableList.add(p);
        }
        List<PartiallyComparableAdvisorHolder> sortedList=PartialOrder.sort(partialComparableList);
        List<Advisor> result=new ArrayList<>(sortedList.size());
        for(PartiallyComparableAdvisorHolder holder:sortedList)
        {
            result.add(holder.getAdvisor());
        }
        return null;
    }

    /**
     * 默认为空方法,留给子类扩展,主要用于某些子类creator
     * 需要某些advisor时在此处创建并加入advisor链
     */
    @Override
    protected void extendAdvisor(List<Advisor> advisorList)
    {
        advisorList.add(new DefaultPointcutAdvisor(ExposeInvocationInterceptor.INSATNCE));
    }

    private static class PartiallyComparableAdvisorHolder implements PartialOrder.PartialComparable
    {

        @Override
        public int fallbackCompareTo(Object other)
        {
            return 0;
        }

        private final Advisor advisor;

        private final Comparator<Advisor> comparator;

        public PartiallyComparableAdvisorHolder(Advisor advisor, Comparator<Advisor> comparator)
        {
            this.advisor = advisor;
            this.comparator = comparator;
        }

        @Override
        public int compareTo(Object obj)
        {
            Advisor otherAdvisor = ((PartiallyComparableAdvisorHolder) obj).advisor;
            return this.comparator.compare(this.advisor, otherAdvisor);
        }


        public Advisor getAdvisor()
        {
            return this.advisor;
        }


    }

}
