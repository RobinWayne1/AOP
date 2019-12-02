package myframework.aop.anntations;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.*;

/**
 * @author Robin
 * @date 2019/11/28 -22:58
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AroundAdvices.class)
public @interface AroundAdvice
{
    @NotNull
    String value();
}
