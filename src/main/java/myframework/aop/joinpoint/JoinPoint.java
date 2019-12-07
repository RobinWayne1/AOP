package myframework.aop.joinpoint;

/**
 * 在AbstractAspectAdvice中的currentJointPoint()方法中要用到(创建),而
 * 这个方法在argBinding()中用到,作为被绑定的参数传入进增强方法中
 * @author Robin
 * @date 2019/12/7 -11:14
 */
public interface JoinPoint
{

    Object getThis();

    Object[] getArgs();
}
