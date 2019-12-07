package myframework.aop.advice;

/**
 * 所有增强方法必须实现该接口
 * @author Robin
 * @date 2019/11/29 -16:06
 */
public interface MethodInterceptor extends Advice
{
    Object invoke();
}
