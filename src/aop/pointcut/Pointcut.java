package aop.pointcut;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Robin
 * @date 2019/11/28 -21:34
 */
//切点类,声明切点方法符合的类和方法
public class Pointcut
{
    private Set<Method> methods=new HashSet<>();

    private Set<Class> classes=new HashSet<>();
}
