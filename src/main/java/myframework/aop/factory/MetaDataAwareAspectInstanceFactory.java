package myframework.aop.factory;

import myframework.aop.metadata.AspectMetadata;
import myframework.aop.metadata.AspectMetadata;

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
    AspectMetadata getAspectMetaData();
}
