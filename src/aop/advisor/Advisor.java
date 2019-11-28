package aop.advisor;

import aop.advice.Advice;
import aop.pointcut.Pointcut;

/**
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
