package myframework.aop.advisor;

import myframework.aop.advice.Advice;
import myframework.aop.pointcut.Pointcut;

/**
 *
 * 用来封装Advice和Pointcut,真正应用的时候取Advice应用
 * @author Robin
 * @date 2019/11/28 -21:10
 */
public interface Advisor
{


    /**
     * 空实现以填充字段
     */
    Advice EMPTY_ADVICE=new Advice()
    {
    };

    /**
     * 由子类持有切点
     * @return
     */
    Pointcut getPointcut();


    /**
     * 由子类持有增强
     * @return
     */
    Advice getAdvice();
}
