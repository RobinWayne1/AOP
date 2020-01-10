package aspect;

import myframework.aop.anntations.*;
import myframework.aop.joinpoint.JoinPoint;
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
    @Before("* service.impl.* hello(java.lang.String,java.lang.String,java.lang.String)")
    public void before2(JoinPoint jp)
    {
        Object[] args = jp.getArgs();
        System.out.println("before2;args:");
        for (Object arg : args)
        {
           System.out.print(","+arg);
        }
    }
    @Before("* service.impl.* hello(java.lang.String,java.lang.String,java.lang.String)")
    public void before22(JoinPoint jp)
    {
        Object[] args = jp.getArgs();
        System.out.println("before22;args:");
        for (Object arg : args)
        {
            System.out.print(","+arg);
        }
    }
    @After("java.lang.String service.impl.* hello1()")
    public void after2()
    {
        System.out.println("after2");
    }

    @Around("java.lang.Object service.impl.UserServiceImpl *(..)")
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
