package myframework.util;

import myframework.aop.advice.Advice;
import myframework.aop.advisor.Advisor;
import myframework.aop.advice.impl.BeforeAdvice;

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
public class ConfigUtil
{
    private static final String PROPERTIES_PATH = "/framework.properties";

    private static final Properties config;


    static
    {
        Properties prop = new Properties();
        config = prop;
        try
        {
            //这个getResourceAsStream方法就是把文件转为inputStream的方式
            prop.load(ConfigUtil.class.getResourceAsStream(PROPERTIES_PATH));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static String[] getProxyClass()
    {
        String[] clazz = config.getProperty("framework.aop.proxy-class").split(",");
        System.out.println(clazz);
        return clazz;
    }

    public static String[] getServicePackage()
    {
        String[] packages=config.getProperty("framework.service-scan-package").split(",");
        return packages;
    }

}
