package com.aircraft.codelab.labcore.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * 2022-07-17
 * 后置处理器 初始化前后处理
 *
 * @author tao.zhang
 * @since 1.0
 */
//@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeforeInitialization" + "===>" + bean + "===>" + beanName);
        return bean;
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("AfterInitialization" + "===>" + bean + "===>" + beanName);
        return bean;
    }
}
