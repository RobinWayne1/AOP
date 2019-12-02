package myframework.exception;

/**
 * @author Robin
 * @date 2019/11/30 -13:58
 */
public class BeanInstantiateException extends RuntimeException
{
    public BeanInstantiateException(Class cl, String info)
    {
        super(cl.getName()+":"+info);
        this.printStackTrace();
    }
}
