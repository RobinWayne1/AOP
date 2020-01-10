package aspect;

import myframework.aop.anntations.After;
import myframework.aop.anntations.Aspect;
import myframework.aop.anntations.Before;
import myframework.aop.anntations.Order;
import myframework.aop.joinpoint.JoinPoint;

/**
 * @author Robin
 * @date 2019/11/27 -11:25
 */
@Order(1)
@Aspect
public class UserServiceAspect
{
    //返回类型 类路径(准确点说,是实现类的类路径)
    @Before("* service.impl.UserServiceImpl *(..)")
    public void before1(JoinPoint joinPoint)
    {
        System.out.println("before1"+"args:"+joinPoint.getArgs()+joinPoint.getThis());
    }

    @After("* service.impl.UserInfoServiceImpl *(..)")
    public void after1(){System.out.println("after1");}
}
