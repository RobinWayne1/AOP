package myframework.aop.pointcut.impl;

import myframework.aop.pointcut.ClassFilter;
import myframework.aop.pointcut.MethodMatcher;
import myframework.aop.pointcut.PointCut;
import myframework.exception.AspectAnnotationException;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 注意此处的成员变量都是对Pointcut的阐释(即目标方法,而不是增强方法)
 * @author Robin
 * @date 2019/11/28 -21:34
 */
//切点类,声明切点方法符合的类和方法
public class AspectExpressionPointcut implements ClassFilter, MethodMatcher, PointCut
{
    private String returnType;

    private Class<?>[] parameterTypes;

    private String expression;

    private String methodName;

    private Class<?> aspectClass;
    /**
     * 表达式上所需增强的类
     */
    private String targetClass;


//    public AspectExpressionPointcut(Class<?> returnType, Class<?>[] parameterType, Integer parameterCount, String expression)
//    {
//        this.returnType = returnType;
//        this.parameterType = parameterType;
//        this.parameterCount = parameterCount;
//        this.expression = expression;
//    }

    public AspectExpressionPointcut(Class aspectClass)
    {
        this.aspectClass = aspectClass;
    }

    public void setExpression(String expression)
    {

        this.expression = expression;
        //split从左边开始算起,比如三个空格,split空格就会得到一个空格,看blog
        String[] ele = expression.split(" ");
        if (ele.length != 3)
        {
            throw new AspectAnnotationException(this.aspectClass, "Bad aspect method expression format!");
        }
        this.returnType = ele[0];
        this.targetClass = ele[1];
        this.methodName = ele[2].substring(0, ele[2].indexOf("\\(") - 1);
        String parameters = ele[2].substring(ele[2].indexOf("\\(") + 1, ele[2].length() - 1);
        if (parameters.equals(".."))
        {
            this.parameterTypes = ALL_PARAMETERS;
        } else
        {
            try
            {
                //如果是""则会返回"",表示无参数  ( )所以当无参数时写法应该是空格,即()和(..)作用一样,代表任意参数
                String[] parameter = parameters.split(",");
                Class<?>[] parameterType = new Class[parameter.length];
                for (int p = 0; p < parameter.length; p++)
                {
                    //如果为""这里就会报错,需要修改方法
                    parameterType[p] = Class.forName(parameter[p]);
                }
                this.parameterTypes=parameterType;
            } catch (ClassNotFoundException e)
            {
                throw new AspectAnnotationException(this.aspectClass, "Bad aspect method expression format or parameters type no exist!");
            }
        }

    }


    @Override
    public MethodMatcher getMethodMatcher()
    {
        return this;
    }

    @Override
    public ClassFilter getClassFilter()
    {
        return this;
    }

    @Override
    public boolean matches(Class targetClass)
    {
        //如果目标类是*即为某包下的所有方法
        if(this.targetClass.endsWith("*"))
        {
            return this.targetClass.startsWith(targetClass.getPackage().getName());
        }
        else
        {
            return this.targetClass.equals(targetClass.getName());
        }

    }

    /**
     * 用来和受到代理的方法匹配并返回结果,在classFilter通过之后调用
     *
     * 重点测试对象
     * @param proxyMethod
     * @return
     */
    @Override
    public boolean matches(Method proxyMethod)
    {
        //先判断方法名
       if(this.methodName.equals("*"))
       {

           if(this.parameterTypes!=ALL_PARAMETERS)
           {
               return Arrays.equals(this.parameterTypes,proxyMethod.getParameterTypes());
           }
           return true;

       }
        return false;
    }
}
