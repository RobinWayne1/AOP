package myframework.aop.advice;

/**
 * @author Robin
 * @date 2019/11/29 -16:06
 */
public interface AspectAdvice extends Advice
{
    Object invoke();
}
