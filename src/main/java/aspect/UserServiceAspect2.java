package aspect;

import myframework.aop.anntations.*;
import myframework.aop.joinpoint.ProceedingJoinPoint;

/**
 * @author Robin
 * @date 2020/1/9 -21:54
 */
@Aspect
@Order(2)
public class UserServiceAspect2
{
    //返回类型 类路径(准确点说,是实现类的类路径)
    @Before("* service.impl.* hello(..)")
    public void before2()
    {
        System.out.println("before1");
    }

    @After("* service.impl.* hello(java.lang.String)")
    public void after2(){System.out.println("after1");}
    @Around("* service.impl.UserServiceImpl hello()")
    public Object aroundHello2(ProceedingJoinPoint p)
    {
        System.out.println("before2HelloAround");
        Object o = null;
        try
        {

            o = p.proceed();

        } catch (Throwable e)
        {
            e.printStackTrace();
        }
        System.out.println("after2HelloAround");
        return o;
    }
}
