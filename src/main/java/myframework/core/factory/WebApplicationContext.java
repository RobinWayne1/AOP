package myframework.core.factory;

/**
 * @author Robin
 * @date 2019/12/1 -11:18
 */
public interface WebApplicationContext extends BeanFactory
{
    String ROOT_CONTEXT_NAME="CAFEBABE."+ WebApplicationContext.class.getName() + ".ROOT";
}
