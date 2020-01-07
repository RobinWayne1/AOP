package myframework.aop;

import myframework.aop.advice.Advice;
import myframework.aop.advice.impl.AfterAdvice;
import myframework.aop.advice.impl.AroundAdvice;
import myframework.aop.advice.impl.BeforeAdvice;
import myframework.aop.advisor.Advisor;
import myframework.core.order.comparator.AnnotationAwareOrderComparator;
import myframework.core.order.comparator.InstanceComparator;
import myframework.util.AspectAopUtils;

import java.util.Comparator;

/**
 * 这里与SpringAOP的AspectJPrecedenceComparator不同,做了点改进(也不知道是不是改进)
 * <p>
 * SpringAOP的思路是:首先BeanFactoryAspectJAdvisorBuilder遍历beanName找出切面类去getAdvisors(),
 * 在getAdvisorMethod()(即获取切面类的方法)中获取后按照 在ReflectiveAspectJAdvisorFactory的静态块
 * 中定义的初次排序顺序(around→before→after)使用list.sort和InstanceComparator类进行排序,最后返回到builder
 * 将切面里初次排序完成的advisor加入到list中,注意此时list中切面与切面之间是乱序的
 * <p>
 * 在执行advisorList的相对于执行后处理器的bean过滤之后,执行最终排序,即使用PartialOrder.sort,利用此方法
 * 的compare方法进行最终的排序
 * <p>
 * <p>
 * 这里我的思路:由于不清楚为什么需要初次排序排成那样奇怪的顺序然后再在最终排序时以afterAdvisor为特例
 * 进行排序,我认为初次排序完全可以不需要,直接在这个Comparator中直接将一个切面的advisor排成
 * (after→around→before),这样反而感觉简单一点
 * <p>
 * 好像有点理解了,InstanceComparator中定义的顺序是以注解.class来定义的,并不是接口
 * 在执行extendAdvisor()方法之后可能会有只实现了MethodInterceptor的增强加入进advisorsList中,
 * 此时就会同时有advice和advisor同时在list??没有的,所有advice都要受到advisor包装,ExposeMethodInterceptor也一样
 * <p>
 * 也有可能是因为Spring的advice类图中afterReturning和afterThrowing接口都继承afterAdvice接口,而后置增强
 * 只继承afterAdvice接口,所以这两种是分不出来的.由于我还没有做上述两种,所以这里暂时不考虑
 *
 * @author Robin
 * @date 2020/1/5 -22:27
 */
public class AspectPrecedenceComparator implements Comparator<Advisor>
{

    private static final int HIGHER_PRECEDENCE = -1;

    private static final int SAME_PRECEDENCE = 0;

    private static final int LOWER_PRECEDENCE = 1;

    /**
     * 用作比较advisor所属切面的order
     */
    private final Comparator<? super Advisor> advisorAspectComparator;

    /**
     * 执行切面内增强的逻辑顺序
     */
    private static final Comparator<? super Advice> ADVICE_COMPARATOR =
            new InstanceComparator<>(AfterAdvice.class, AroundAdvice.class, BeforeAdvice.class);

    public AspectPrecedenceComparator(Comparator<? super Advisor> advisorAspectComparator)
    {
        this.advisorAspectComparator = advisorAspectComparator;
    }

    public AspectPrecedenceComparator()
    {
        this.advisorAspectComparator= AnnotationAwareOrderComparator.INSTANCE;
    }

    @Override
    public int compare(Advisor o1, Advisor o2)
    {
        //查看定义的增强是否有Order()注解控制顺序
        //这里使用的comparator是{@link AnnotationAwareOrderComparator}
        int advisorAspectPrecedence = advisorAspectComparator.compare(o1,o2);

        //如果两个相同切面的增强且Order顺序相同
        if (advisorAspectPrecedence == SAME_PRECEDENCE && declaredInSameAspect(o1, o2)) {
            advisorAspectPrecedence = comparePrecedenceWithinAspect(o1.getAdvice(), o2.getAdvice());
        }

        return advisorAspectPrecedence;
    }

    private int comparePrecedenceWithinAspect(Advice o1, Advice o2)
    {
        return ADVICE_COMPARATOR.compare(o1,o2);
    }

    private boolean declaredInSameAspect(Advisor advisor1, Advisor advisor2)
    {
        return (hasAspectName(advisor1) && hasAspectName(advisor2) &&
                getAspectName(advisor1).equals(getAspectName(advisor2)));
    }

    private boolean hasAspectName(Advisor anAdvisor)
    {
        return (anAdvisor instanceof AspectPrecedenceInformation ||
                anAdvisor.getAdvice() instanceof AspectPrecedenceInformation);
    }

    // pre-condition is that hasAspectName returned true
    private String getAspectName(Advisor anAdvisor)
    {
        AspectPrecedenceInformation pi = AspectAopUtils.getAspectPrecedenceInformationFor(anAdvisor);
        if (pi == null)
        {
            throw new IllegalStateException(anAdvisor + ":Unresolvable precedence information");
        }
        return pi.getAspectName();
    }
}
