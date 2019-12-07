package myframework.aop.factory;

import myframework.aop.metadata.AspectMetaData;

/**
 * @author Robin
 * @date 2019/12/7 -20:50
 */
public interface MetaDataAwareAspectInstanceFactory extends AspectInstanceFactory
{

    /**
     * 返回切面元信息
     * @return
     */
    AspectMetaData getAspectMetaData();
}
