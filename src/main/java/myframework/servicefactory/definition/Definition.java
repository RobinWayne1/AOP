package myframework.servicefactory.definition;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @author Robin
 * @date 2019/11/29 -20:22
 */
public interface Definition
{
    Set<Method> getMethods();

    Class getServiceClass();

    String getServiceName()

//    /**
//     * 将Class与类方法映射存入Map,可供AOP模块使用
//     *
//     * @param cl
//     * @param methods
//     */
//    void putDefinitionMap(Class cl, Set<Method> methods);
//
//    /**
//     * 将serviceName与Class对象存入Map,以供反射实例化使用
//     *
//     * @param serviceName
//     * @param cl
//     */
//    void putClassMap(String serviceName, Class cl);
//
//    Class getClassMap(String name);
//
//    Set<Method> getDefinitionMap(Class cl);
//
//    List<Class> getClassMap();
}
