package myframework.servicefactory.context;

import myframework.servicefactory.context.impl.WebApplicationContext;

/**
 * @author Robin
 * @date 2019/11/29 -12:32
 */
public interface Context
{

    String ROOT_CONTEXT_NAME="CAFEBABE."+ Context.class.getName() + ".ROOT";
    /**
     * 从ObjectFactory中返回指定bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
