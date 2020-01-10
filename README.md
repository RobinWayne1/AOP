# AOP
### 1、概述
* myframework包下是框架的具体内容。
* 此框架模仿SpringAOP，做了些许修改和对AOP核心模块所有涉及到的类附上了注释。（注释部分稍乱，有空还需做整理）
* 框架面向的用户是Servlet使用者，此框架实现了调用Service层时的单例模式与对Service层组件的切面编程
* 此框架的执行思路与Spring大致相同：根据framework.properties的配置(其中配置了需要扫描的包的位置)将所有service层的组件利用反射创建出来,然后利用实现了`BeanPostProcessor`的`AnnotationAwareAutoProxyCreator`类 中的`postProcessAfterInitialization()`方法,在每个Bean创建完成之后进行后置处理,然后返回容器BeanMap一个代理对象(注意此框架所有bean都是非延迟加载).
### 2、使用方法
* 与Spring类似，本框架提供了BeanFactoryAware接口，用户只需要实现此接口的`setBeanFactory()`方法,框架就会在bean反射创建完成之后的`initalizeBean()`将BeanFactory实例传到用户代码中(注:此类需要添加到bean扫描路径下作为bean放入容器中)。然后再使用BeanFactory中的`getBean()`方法就可获取bean实例(注:beanId为类名的小写)。
* framework.properties的配置有两种:1、framework.bean-scan-package；2、framework.aop.auto-proxy；前者是Bean扫描路径(切面类,Service层组件路径都要放入其中)，后者是是否使用自动代理，而由于本框架只实现了基于注解的代理，所以这个键亦指是否启用AOP
* 切面类的定义：1、`@Aspect` 使用了此注解的Bean代表着切面类2、`@Order()`此注解与SpringAOP功能一致,表示启用切面增强的顺序,优先级越高Order的值越小,执行时也会在更外层(即V型结构)
* 增强的定义:目前仅支持三种增强:前置增强、后置增强、环绕增强，分别为`@Before、@Afte、@Around`.
* 切点表达式:此处与SpringAOP有较大不同,示例:`@Before("* service.impl.* hello(java.lang.String,java.lang.String,java.lang.String)")` 其中第一个`'*'`代表匹配返回类型,这是第一部分。第二部分为空格后的一部分，代表匹配的类路径，`···*`代表某包下的所有类，示例中即指`service.impl`包下的所有类。第三部分为第二部分空格后的一部分，代表匹配方法名与参数，`'*'`代表所有方法。此外仍需重点注意的是(..)代表任意参数,()代表无参数。
* 可以观看具体demo来对应使用。
### 3、思路异同
本人水平较差，AOP模块的代码大部分是模仿SpringAOP，其中有两处比较大的思路修改。其一是ClassFilter和MethodMatcher，由于重新定义了切点表达式，所以这两个模块完全由自己编写，Spring中这模块的具体实现我也没有过多了解，所以不展开讨论。而我想讨论的是其二：advisor的排序思路。SpringAOP的排序思路：在进行以切面类为单位的增强查找前，使用`getAdvisorMethods()`方法进行了初次排序(以AroundAdvice→BeforeAdvice→AfterAdvice为顺序),这个排序是根据方法上的注解类型排序的。然后Spring以这个切面方法顺序，构造包含切点信息与Advice实例的Advisor，然后判断Advisor是否匹配当前bean的某个方法，如果匹配则将该增强加入候选增强中，此时将切面类方法过滤了一遍。此后在`sortAdvisor()`方法中对所需Advisors进行第二次排序。排序利用aspectJ包下的PartialOrder类，并利用AspectJPrecedenceComparator这个比较器的独特比较规则(根据初次排序得到的增强顺序,如果两个增强都没有一个属于前置增强,则按照初次排序的顺序,顺序越前则优先级越高;而如果其一属于前置增强,则按照初次排序的顺序,顺序越前优先级越低)进行了偏序排序。为什么要使用偏序这个概念进行排序而不只是利用`List.sort()`方法排序,理论上所有的Advisor都应该是可比较的，这是我的其中一个疑问。其二，为什么初次排序要排列成这么奇怪的顺序，然后在最终排序时又把顺序倒回AfterAdvice→AroundAdvice→BeforeAdvice(猜测答案会在我没有深入了解的AfterReturningAdvice与AfterThrowingAdvice中)。基于上面两个疑问，我将排序这个模块修改成了较为简单的实现，即删除初次排序，仍旧利用PartialOrder类，在AspectJPrecedenceComparator这个比较器中直接定义符合最终调用顺序的比较(即AfterAdvice→AroundAdvice→BeforeAdvice)。测试得出我的思路可行。如果有大佬明白其中道理还望告知小弟。
### 4、总结
这次AOP源码学习大概花费一个月，其中测试仅仅只花了两天。。。Bug可能会有一些，如果有读者愿意试用并提出建议，本人不胜感激。算是第一次写博客，如有不妥之处敬请见谅。
