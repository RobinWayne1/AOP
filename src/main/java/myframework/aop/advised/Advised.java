package myframework.aop.advised;

import myframework.aop.advisor.Advisor;

/**
 * @author Robin
 * @date 2019/12/9 -15:53
 */
public interface Advised
{
    void addAdvisor(Advisor advisor);

    void setPreFiltered(boolean preFiltered);

    boolean isPreFiltered();

    Advisor[] getAdvisors();
}
