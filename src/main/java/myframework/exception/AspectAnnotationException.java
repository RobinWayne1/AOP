package myframework.exception;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/12/4 -13:22
 */
public class AspectAnnotationException extends RuntimeException
{
    public AspectAnnotationException(Method m,String info)
    {
        super(m+":"+info);
        this.printStackTrace();
    }
    public AspectAnnotationException(Class cl,String info){
        super(cl+":"+info);
        this.printStackTrace();
    }
}
