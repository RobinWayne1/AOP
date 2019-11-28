package aop.util;

import aop.advice.Advice;
import aop.advisor.Advisor;
import aop.advice.impl.BeforeAdvice;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

/**
 * @author Robin
 * @date 2019/11/28 -17:25
 */

//获取切面类并解析为advice
public class AopConfigUtil
{
    private static final String PROPERTIES_PATH = "/aop.properties";

    private static final Properties aopConfig;

    static
    {
        Properties prop = new Properties();
        aopConfig = prop;
        try
        {
            //这个getResourceAsStream方法就是把文件转为inputStream的方式
            prop.load(AopConfigUtil.class.getResourceAsStream(PROPERTIES_PATH));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static String[] getProxyClass()
    {
        String[] clazz = aopConfig.getProperty("aop.proxy-class").split(",");
        System.out.println(clazz);
        return clazz;
    }

    /**
     * 解析advice,用advisor包装,此方法放在creator构造器,wrap前
     */
    public static List<Advisor> findAdvisors()
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
                    for(Annotation annotation:annotations)
                    {
                       if(annotation.annotationType().equals(BeforeAdvice.class))
                       {
                           //在此之前先验证目标方法的存在性
                           Advice advice=new BeforeAdvice();
                       }
                    }
                }
            }
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    private Advisor parse()
    {

    }
}
