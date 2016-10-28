package spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Created by williaz on 10/18/16.
 * Showcase for Spring bean lifecycle
 *
 * #Both @Qualifer and @Resource can't be used on constructor
 * # @Autowired == @Inject == @Resource
 * # @Autowired + @Qualifier(name) with Both @Bean(name) or @Bean @Qualifier(name) == @Resource(name) with @bean(name)
 *
 * # to address ambiguity in autowiring:
 * 1. @primary: @Primary can be used either alongside @Component for beans that are component-scanned
 *    or alongside @Bean for beans declared in Java configuration.
 *  -->when more than one bean is designated as primary, there are no primary candidates.
 *
 * 2. @Qualifier with @Autowired or @Inject
 *
 * 3. @Resource (it check the value, cannot be used on constructor, @Autowired check parameter)
 *
 * #A bean post processor allows bean processing before and after the initialization callback method
 * (i.e., the one assigned to the initmethod attribute of the @Bean annotation
 * or the method decorated with the @PostConstruct annotation).
 *
 */
@Component
public class BeanLife implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean{
    @Value("Bean life cycle")
    private String name;

    private int days;

    private String otherName;

    private BeanInjected BI;


    // autowire constructor one bean @Autowired == @Inject
    @Autowired
    //@Inject
    public BeanLife(BeanInjected beanC) {
        System.out.println("1. Constructor Injection.");
        this.days = beanC.getNums();
    }

    //autowire setter one bean


    //@Autowired
    @Inject
    @Qualifier("beanS")
    public void setOtherName(BeanInjected bean) {
        System.out.println("3. Setter Injection.");
        this.otherName = bean.getName();
    }

    @Resource(name = "beanR")
    public void setBI(BeanInjected BI) {
        System.out.println("2. Setter BI Injection.");
        this.BI = BI;
    }

    //implements InitializingBean, DisposableBean

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("6. call afterPropertiesSet of InitializingBean.");

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("9. call destroy of DisposableBean.");

    }

    //@PostConstruct, @PreDestroy
    @PostConstruct
    public void afterConstruct(){
        System.out.println("5. call PostConstruct.");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("8. call PreDestroy.");
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

        System.out.println("A2 BeanFactoryAware: "+ beanFactory.getClass().getSimpleName());
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("A1 BeanNameAware: "+ name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("A3 ApplicationContextAware: "+ applicationContext.getClass().getSimpleName());
    }

    //TODO @Bean(initMethod, destroyMethod)
    //implements BeanPostProcssor

    //getters
    public String getName() {
        return name;
    }

    public int getDays() {
        return days;
    }

    public String getOtherName() {
        return otherName;
    }

    public BeanInjected getBI() {
        return BI;
    }

    //getters end


}

