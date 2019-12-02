package myframework.aop.proxy;

/**
 *
 * 需要存放chainfactory,每个实例一个JdkDynamicAopProxy,只存放以本实例为targetclass的advisor,其中一个JdkDynamicAopProxy有多个拦截器链
 * ,不同的拦截器链对应不同的方法
 * @author Robin
 * @date 2019/11/30 -23:21
 */
public class JdkDynamicAopProxy
{
}
