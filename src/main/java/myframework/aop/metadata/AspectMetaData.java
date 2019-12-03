package myframework.aop.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Robin
 * @date 2019/12/3 -16:34
 */
public class AspectMetaData
{
    private final String aspectName;

    private final Class<?> aspectClass;

    private final Map<Method, Annotation> adviceMethodInfoMap;

    public AspectMetaData(String aspectName, Class<?> aspectClass, Map<Method, Annotation> adviceMethodInfoMap)
    {
        this.aspectName = aspectName;
        this.aspectClass = aspectClass;
        this.adviceMethodInfoMap = adviceMethodInfoMap;
    }

    public String getAspectName()
    {
        return aspectName;
    }

    public Class<?> getAspectClass()
    {
        return aspectClass;
    }

    public Map<Method, Annotation> getAdviceMethodInfoMap()
    {
        return adviceMethodInfoMap;
    }
}
