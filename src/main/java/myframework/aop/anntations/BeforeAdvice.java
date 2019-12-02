package myframework.aop.anntations;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.*;

/**
 * @author Robin
 * @date 2019/11/27 -11:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface BeforeAdvice
{
    @NotNull
    String value();
}
