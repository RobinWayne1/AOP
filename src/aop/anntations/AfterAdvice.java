package aop.anntations;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Robin
 * @date 2019/11/28 -22:59
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterAdvice
{
    @NotNull
    String value();
}
