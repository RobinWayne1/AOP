package aop.advice;

/**
 * @author Robin
 * @date 2019/11/28 -17:18
 */
//存放切面类,方法名,代理方法名
public interface Advice
{
    Object invoke();
}
