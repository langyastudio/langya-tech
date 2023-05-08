package com.langyastudio.springboot.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * spring 上下文工具类
 *
 * @author 江加雄
 */
@Component
public class SpringContextUtils implements ApplicationContextAware, EnvironmentAware {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;
    private static Environment        environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        SpringContextUtils.environment = environment;
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static Environment getEnvironment() {
        return environment;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     *
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     *
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     *
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static String getEnvProperty(String key) {
        return getEnvironment().getProperty(key);
    }

    public static <T> T getEnvProperty(String key, Class<T> cls) {
        return getEnvironment().getProperty(key, cls);
    }

    public static <T> T getEnvProperty(String key, Class<T> cls, T def) {
        return getEnvironment().getProperty(key, cls, def);
    }
}
