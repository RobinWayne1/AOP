package myframework.aop.framework.proxy.factory.impl;

import myframework.aop.advised.AdvisedSupport;
import myframework.aop.framework.proxy.AopProxy;
import myframework.aop.framework.proxy.factory.AopProxyFactory;
import myframework.aop.framework.proxy.impl.JdkDynamicAopProxy;
import myframework.exception.AopConfigException;

/**
 * @author Robin
 * @date 2019/12/9 -19:48
 */
public class DefaultAopProxyFactory implements AopProxyFactory
{
    /**
     * 创建一个AopProxy,目前只支持Jdk动态代理
     *
     * @param config
     * @return
     */
    @Override
    public AopProxy createAopProxy(AdvisedSupport config)
    {
        if(config.getInterfaces().size()!=0)
        {
            return new JdkDynamicAopProxy(config);
        }
        else{
            throw new AopConfigException("JdkDynamicProxy must have specific interfaces");
        }
    }
}
