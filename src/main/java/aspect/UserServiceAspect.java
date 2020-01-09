package aspect;

import myframework.aop.anntations.After;
import myframework.aop.anntations.Aspect;
import myframework.aop.anntations.Before;
import myframework.aop.anntations.Order;

/**
 * @author Robin
 * @date 2019/11/27 -11:25
 */
@Order(1)
@Aspect
public class UserServiceAspect
{
    //返回类型 类路径(准确点说,是实现类的类路径)
    @Before("* service.impl.* *(..)")
    public void before1()
    {
        System.out.println("before1");
    }

    @After("* service.impl.* *(..)")
    public void after1(){System.out.println("after1");}
}
