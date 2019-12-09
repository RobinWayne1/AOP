package aspect;

import myframework.aop.anntations.After;
import myframework.aop.anntations.Aspect;
import myframework.aop.anntations.Before;

/**
 * @author Robin
 * @date 2019/11/27 -11:25
 */
@Aspect()
public class UserServiceAspect
{
    //返回类型 类路径(准确点说,是实现类的类路径)
    @Before("* service.UserService.* *(..)")
    @After("* service.UserService.* *(..)")
    public void say()
    {
        System.out.println("yes");
    }
}
