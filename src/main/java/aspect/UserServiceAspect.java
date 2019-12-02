package aspect;

import myframework.aop.anntations.AfterAdvice;
import myframework.aop.anntations.Aspect;
import myframework.aop.anntations.BeforeAdvice;
import service.UserService;

/**
 * @author Robin
 * @date 2019/11/27 -11:25
 */
@Aspect(UserService.class)
public class UserServiceAspect
{
    //返回类型 类路径
    @BeforeAdvice("* service.UserService *.*(..)")
    @AfterAdvice("* service.UserService *.*(..)")
    @AfterAdvice("* service.UserService *.*(..)")
    public void say()
    {
        System.out.println("yes");
    }
}
