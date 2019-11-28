import aop.anntations.Aspect;
import aop.util.AopConfigUtil;

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
        try
        {
            String[] aspectClasses = AopConfigUtil.getProxyClass();
            for (String className : aspectClasses)
            {
                Class aspectClass = Class.forName(className);
                Method[] method=aspectClass.getDeclaredMethods();
                for(Method m:method)
                {
                    Annotation[]annotations=m.getAnnotations();

                }
            }
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
