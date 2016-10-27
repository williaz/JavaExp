package spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by williaz on 10/27/16.
 */
@Component
public class CheckNamePostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        System.out.println("In BeanPostProcessor before Initialization: "+ beanName +" which is class "+ bean.getClass().getSimpleName()+" Got checked");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("In BeanPostProcessor After Initialization: "+ beanName +" which is class "+ bean.getClass().getSimpleName()+" Got checked");
        return bean;
    }
}
