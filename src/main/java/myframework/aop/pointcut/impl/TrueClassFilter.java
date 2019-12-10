package myframework.aop.pointcut.impl;

import myframework.aop.pointcut.ClassFilter;

/**
 * @author Robin
 * @date 2019/12/10 -16:27
 */
public class TrueClassFilter implements ClassFilter
{
    public static final TrueClassFilter INSTANCE=new TrueClassFilter();

    /**
     * 保证单例
     */
    private TrueClassFilter()
    {}
    /**
     * 过滤类
     *
     * @param targetClass
     * @return
     */
    @Override
    public boolean matches(Class targetClass)
    {
        return true;
    }
}
