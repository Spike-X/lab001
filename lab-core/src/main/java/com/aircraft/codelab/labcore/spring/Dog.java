package com.aircraft.codelab.labcore.spring;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 2022-07-17
 *
 * @author tao.zhang
 * @since 1.0
 */
@Component
public class Dog {
    public Dog() {
        System.out.println("dog constructor");
    }

    // 对象创建并赋值
    @PostConstruct
    public void init() {
        System.out.println("dog init");
    }

    // 容器移除对象之前
    @PreDestroy
    public void destroy() {
        System.out.println("dog destroy");
    }
}
