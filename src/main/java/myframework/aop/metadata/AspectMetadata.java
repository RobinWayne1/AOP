package myframework.aop.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 切面类的元信息,spring中的此类有一个功能就是判断当前切面类是要设置成原型还是单例
 *
 * @author Robin
 * @date 2019/12/3 -16:34
 */
public class AspectMetadata
{
    private final String aspectName;

    private final Class<?> aspectClass;

    /**
     * 需要修改的地方，map的keySet()是无序的,所以不能这样搞进来,只能用List存储
     */
//    private final Map<Method, Annotation> adviceMethodInfoMap;

    public AspectMetadata(String aspectName, Class<?> aspectClass)
    {
        this.aspectName = aspectName;
        this.aspectClass = aspectClass;
//        this.adviceMethodInfoMap = adviceMethodInfoMap;
    }

    public String getAspectName()
    {
        return aspectName;
    }

    public Class<?> getAspectClass()
    {
        return aspectClass;
    }

}
