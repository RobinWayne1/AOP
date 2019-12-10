package myframework.aop.pointcut;

import myframework.aop.pointcut.impl.TrueClassFilter;

/**
 * 用于对pointcut和目标类进行匹配
 *
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

    /**
     * <p>由于此类只是一个用于判断的flag,所以没必要创建对象<p>
     * 记得接口定义的成员变量默认是public static final
     */
    ClassFilter TRUE= TrueClassFilter.INSTANCE;
}
