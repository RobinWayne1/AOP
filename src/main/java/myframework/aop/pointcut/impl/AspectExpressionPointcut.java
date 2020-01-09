package myframework.aop.pointcut.impl;

import myframework.aop.pointcut.ClassFilter;
import myframework.aop.pointcut.MethodMatcher;
import myframework.aop.pointcut.Pointcut;
import myframework.exception.AspectAnnotationException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 注意此处的成员变量都是对Pointcut的阐释(即目标方法,而不是增强方法)
 *
 * 此Pointcut非Spring的poingcut,应该是不同的
 * @author Robin
 * @date 2019/11/28 -21:34
 */
//切点类,声明切点方法符合的类和方法
public class AspectExpressionPointcut implements ClassFilter, MethodMatcher, Pointcut
{
    private String returnType;

    private List<Class<?>> parameterTypes;

    private String expression;

    private String methodName;

    private Class<?> aspectClass;
    /**
     * 表达式上所需增强的类
     */
    private String targetClass;

    private boolean isAllParameters = false;


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
        try
        {
            this.returnType = ele[0];
            this.targetClass = ele[1];
            this.methodName = ele[2].substring(0, ele[2].indexOf("("));
            String parameters = ele[2].substring(ele[2].indexOf("(") + 1, ele[2].length() - 1);
            /**
             * 改一下思路,如果是所有参数即(..),则isAllParameters=true,parameterType=emptyList
             * 如果是无参数即(),则isAllParameters=false,parameterType=emptyList
             * 如果是有参数,则isAllParameters=false,parameterType!=emptyList
             */
            //所有参数的情况
            if (parameters.equals(".."))
            {
                this.isAllParameters = true;
                this.parameterTypes = Empty;
            } else
            {
                //无参数的情况
                if (parameters.equals(""))
                {
                    this.parameterTypes = Empty;
                }
                //有参数的情况
                else
                {
                    //如果是""(空串)则会返回"",表示无参数,如果不做上面一步判断则parameter.length()就会变成1则出错
                    String[] parameter = parameters.split(",");
                    List<Class<?>> parameterType = new ArrayList<>(parameter.length);
                    for (int p = 0; p < parameter.length; p++)
                    {
                        parameterType.add(Class.forName(parameter[p]));
                    }
                    this.parameterTypes = parameterType;
                }
            }
        } catch (Exception e)
        {
            throw new AspectAnnotationException(this.aspectClass, "Bad aspect method expression format or parameters type no exist!");
        }

    }

    public String getExpression()
    {
        return expression;
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
        //如果目标类是*即为某包下的所有类
        if (this.targetClass.endsWith(ALL_CLASS))
        {
            return this.targetClass.startsWith(targetClass.getPackage().getName());
        } else
        {
            return this.targetClass.equals(targetClass.getName());
        }

    }

    /**
     * 用来和受到代理的方法匹配并返回结果,在classFilter通过之后调用
     * <p>
     * 重点测试对象
     * <p>
     * <p>
     * 返回类型没判断老哥
     *
     * @param proxyMethod
     * @return
     */
    @Override
    public boolean matches(Method proxyMethod)
    {
        //先判断方法名
        if (this.methodName.equals(ALL_METHODS) || this.methodName.equals(proxyMethod.getName()))
        {
            //得到合法的名字,即不是[Ljava.lang.Object,而是java.lang.Object[]
            if (this.returnType.equals(ALL_TYPE)||this.returnType.equals(proxyMethod.getReturnType().getCanonicalName()))
            {
                /**
                 * 限定没有参数的时候isAllParameters==false和list=empty,所以只要不是isAllParameters为真则都要进行对比,
                 * 因为在没有参数的时候list=empty就会错过判定条件直接返回true了
                 */
                if (!this.isAllParameters)
                {
                    return Arrays.equals(this.parameterTypes.toArray(), proxyMethod.getParameterTypes());
                }
                return true;
            }
        }
        return false;
    }
}
