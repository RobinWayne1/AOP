package myframework.aop.pointcut;

/**
 * @author Robin
 * @date 2019/12/3 -23:20
 */
public interface PointCut
{
     Class<?>[] ALL_PARAMETERS={};

    MethodMatcher getMethodMatcher();

    ClassFilter getClassFilter();
}
