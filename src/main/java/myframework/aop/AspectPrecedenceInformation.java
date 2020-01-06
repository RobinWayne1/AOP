package myframework.aop;

import myframework.core.order.Ordered;

/**
 * @author Robin
 * @date 2019/12/14 -11:18
 */
public interface AspectPrecedenceInformation extends Ordered
{
    /**
     * Return the name of the aspect (bean) in which the advice was declared.
     */
    String getAspectName();

    /**
     * Return the declaration order of the advice member within the aspect.
     */
    int getDeclarationOrder();

    /**
     * Return whether this is a before advice.
     */
    boolean isBeforeAdvice();

    /**
     * Return whether this is an after advice.
     */
    boolean isAfterAdvice();

}

