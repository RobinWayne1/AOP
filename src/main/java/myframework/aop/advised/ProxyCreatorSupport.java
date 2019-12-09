package myframework.aop.advised;

import myframework.aop.proxy.AopProxy;
import myframework.aop.proxy.factory.AopProxyFactory;
import myframework.aop.proxy.factory.impl.DefaultAopProxyFactory;

/**
 * 提供AopProxyFactory,并操作创建AopProxy实例
 *
 * @author Robin
 * @date 2019/12/9 -19:30
 */
public class ProxyCreatorSupport extends AdvisedSupport
{
    private AopProxyFactory aopProxyFactory;

    public ProxyCreatorSupport(AopProxyFactory aopProxyFactory)
    {
        this.aopProxyFactory = aopProxyFactory;
    }

    public ProxyCreatorSupport()
    {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    protected AopProxy createAopProxy()
    {
        return getAopProxyFactory().createAopProxy(this);
    }

    public AopProxyFactory getAopProxyFactory()
    {
        return aopProxyFactory;
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory)
    {
        this.aopProxyFactory = aopProxyFactory;
    }
}
