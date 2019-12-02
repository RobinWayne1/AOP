
import aspect.UserServiceAspect;
import myframework.exception.PropertiesException;
import myframework.util.ConfigUtil;
import myframework.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author Robin
 * @date 2019/11/28 -22:07
 */

public class Test
{
    public static void main(String[]args) throws Exception
    {
//        Class cl=Class.forName("aspect.UserServiceAspect");
//        Method[]m=cl.getDeclaredMethods();
//        for(Method method:m)
//        {
//           Annotation[]annotations= method.getAnnotations();
//            for(Annotation a:annotations)
//            {
//                System.out.println(a);
//            }
//        }
//
        FileUtil.getClasses("service");

    }

}
