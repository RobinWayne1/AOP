package myframework.aop.framework.proxy.factory;

import myframework.aop.advised.AdvisedSupport;
import myframework.aop.framework.proxy.AopProxy;
import myframework.exception.AopConfigException;

/**
 * @author Robin
 * @date 2019/12/9 -19:30
 */
public interface AopProxyFactory
{
    /**
     * 创建一个AopProxy,目前只支持Jdk动态代理
     * @param config
     * @return
     */
    AopProxy createAopProxy(AdvisedSupport config)throws AopConfigException;
}
