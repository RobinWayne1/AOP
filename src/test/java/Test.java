
import myframework.exception.PropertiesException;
import myframework.util.ConfigUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Robin
 * @date 2019/11/28 -22:07
 */

public class Test
{
    public static void main(String[]args) throws IOException
    {
        Properties prop = new Properties();
        InputStream inputStream= ConfigUtil.class.getResourceAsStream("framework.properties");
        if (inputStream==null)
        {
            throw new PropertiesException(Test.class,"wrong");
        }
        prop.load(inputStream);
    }
}
