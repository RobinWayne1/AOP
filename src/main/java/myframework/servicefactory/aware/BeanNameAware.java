package myframework.servicefactory.aware;

/**
 * @author Robin
 * @date 2019/12/2 -18:17
 */
public interface BeanNameAware extends Aware
{
    void setBeanName(String name);
}
