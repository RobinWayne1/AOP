package myframework.aop.pointcut;

/**
 * @author Robin
 * @date 2019/11/28 -21:39
 */
public interface ClassFilter
{
    /**
     * 过滤类
     * @param targetClass
     * @return
     */
    boolean matches(Class targetClass);
}
