package myframework.aop.advised;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Robin
 * @date 2019/12/9 -20:14
 */
public class ProxyFactory extends ProxyCreatorSupport
{
    public Object getProxy()
    {
        return createAopProxy().getProxy();
    }
    public ProxyFactory()
    {

    }
    public ProxyFactory(Object target)
    {
        this.setTarget(target);
        this.setInterfaces(new ArrayList<>(Arrays.asList(target.getClass().getInterfaces())));
    }
}
