
import myframework.util.FileUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/11/28 -22:07
 */
public class Test
{
    public static void main(String[]args)
    {
       System.out.println( FileUtil.getClasses("service.UserService"));
    }
}
