package myframework.exception;

/**
 * @author Robin
 * @date 2019/11/30 -13:00
 */
public class PropertiesException extends RuntimeException
{
    public PropertiesException(Class cl,String info)
    {
        super(cl.getName()+":"+info);
        this.printStackTrace();
    }
}
