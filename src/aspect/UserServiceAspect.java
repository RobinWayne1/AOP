package aspect;

import aop.anntations.Aspect;
import aop.anntations.BeforeAdvice;
import service.UserService;

/**
 * @author Robin
 * @date 2019/11/27 -11:25
 */
@Aspect(UserService.class)
public class UserServiceAspect
{
    @BeforeAdvice("* service.UserService")
    public void say()
    {
        System.out.println("yes");
    }
}
