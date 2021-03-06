package spring;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;

/**
 * Created by williaz on 10/27/16.
 */
@Configuration
@ComponentScan
public class MyConfig {
    @Bean
    @Primary
    public BeanInjected getBeanC(){
        BeanInjected c = new BeanInjected();
        c.setName("Bean C");
        c.setNums(3);

        return c;
    }

    @Bean(name = "beanR")
    //or
    //@Qualifier("beanS")
    public BeanInjected getBeanR(){
        BeanInjected s = new BeanInjected();
        s.setName("Bean R");
        s.setNums(55);

        return s;
    }

    @Bean
    @Qualifier("beanS")
    public BeanInjected getBeanS(){
        BeanInjected s = new BeanInjected();
        s.setName("Bean S");
        s.setNums(21);

        return s;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MyConfig.class);
        applicationContext.refresh();
        BeanLife life = applicationContext.getBean("beanLife", BeanLife.class);
        System.out.println(life.getDays()+ " / "+ life.getName()+ " / "+ life.getOtherName()+" // " +life.getBI());
        life = null;
    }
}
