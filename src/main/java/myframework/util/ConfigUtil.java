package myframework.util;

import myframework.exception.PropertiesException;

import java.io.IOException;
import java.io.InputStream;
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
            InputStream inputStream=ConfigUtil.class.getResourceAsStream(PROPERTIES_PATH);
            if(inputStream==null)
            {
                throw new PropertiesException(ConfigUtil.class,"please put file 'framework.properties' to the resource directory");
            }
            prop.load(inputStream);
        }

        catch (IOException e)
        {
            throw new PropertiesException(ConfigUtil.class,"'framework.properties' IOException");
        }

    }

    public static String[] getProxyClass()
    {
        String[] clazz = config.getProperty("framework.aop.proxy-class").split(",");
        return clazz;
    }

    public static String[] getBeanPackage()
    {
        String[] packages = config.getProperty("framework.service-scan-package").split(",");
        return packages;
    }

}
