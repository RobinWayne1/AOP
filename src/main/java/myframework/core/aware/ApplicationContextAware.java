package myframework.core.aware;

import javafx.application.Application;
import myframework.core.factory.BeanFactory;
import myframework.core.factory.WebApplicationContext;


/**
 * 给用户使用的接口,从而获取WebApplicationContext
 *
 * @author Robin
 * @date 2019/12/12 -0:57
 */
@Deprecated
public interface ApplicationContextAware extends Aware
{
    /**
     * 将applicationContext传给bean
     * @param context
     */
    void setApplicationContext(WebApplicationContext context);
}
