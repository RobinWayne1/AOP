package myframework.aop.advisor;

import myframework.aop.advice.Advice;
import myframework.aop.pointcut.impl.AspectExpressionPointcut;

/**
 *
 * 用来封装Advice和Pointcut,真正应用的时候取Advice应用,并取pointcut匹配advice和目标方法(在pointcutAdvisor中实现)
 * @author Robin
 * @date 2019/11/28 -21:10
 */
public interface Advisor
{

    /**
     * 由子类持有增强
     * @return
     */
    Advice getAdvice();
}
