package myframework.util;

import com.sun.istack.internal.Nullable;
import myframework.aop.AspectPrecedenceInformation;
import myframework.aop.advice.Advice;
import myframework.aop.advisor.Advisor;

/**
 * @author Robin
 * @date 2020/1/7 -20:41
 */
public class AspectAopUtils
{
    @Nullable
    public static AspectPrecedenceInformation getAspectPrecedenceInformationFor(Advisor anAdvisor) {
        if (anAdvisor instanceof AspectPrecedenceInformation) {
            return (AspectPrecedenceInformation) anAdvisor;
        }
        Advice advice = anAdvisor.getAdvice();
        if (advice instanceof AspectPrecedenceInformation) {
            return (AspectPrecedenceInformation) advice;
        }
        return null;
    }
}
