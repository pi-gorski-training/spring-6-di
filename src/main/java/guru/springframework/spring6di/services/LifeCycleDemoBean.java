package guru.springframework.spring6di.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class LifeCycleDemoBean implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware,
        ApplicationContextAware, BeanPostProcessor {

    private String javaVer;

    public LifeCycleDemoBean() {
        System.out.println("## I'm in the LifeCycleDemoBean constructor ##");
    }

    @Value("${java.specification.version")
    public void setJavaVer(String javaVer) {
        this.javaVer = javaVer;
        System.out.println("1. Properties set. Java version = " + this.javaVer);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("2. BeanNameAware - my bean name is " + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("3. BeanFactoryAware - bean factory has been set");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("4. ApplicationContextAware - application context has been set");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("5. PostConstruct annotated method has been called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("6. afterPropertiesSet method has been called");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("7. PreDestroy annotated method has been called");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("8. destroy method has been called");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("# postProcessBeforeInitialization for bean " + beanName);

        if (bean instanceof LifeCycleDemoBean ldb) {
            System.out.println("Calling before init");
            ldb.beforeInit();
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("# postProcessAfterInitialization for bean " + beanName);

        if (bean instanceof LifeCycleDemoBean ldb) {
            System.out.println("Calling after init");
            ldb.afterInit();
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    public void beforeInit() {
        System.out.println("# BeforeInit - called by bean post processor");
    }

    public void afterInit() {
        System.out.println("# AfterInit - called by bean post processor");
    }
}
