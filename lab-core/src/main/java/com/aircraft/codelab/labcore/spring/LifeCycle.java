package com.aircraft.codelab.labcore.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 2022-07-14
 *
 * @author tao.zhang
 * @since 1.0
 */
@ComponentScan("com.aircraft.codelab.labcore.spring")
@Configuration
public class LifeCycle {
//    @DependsOn("cat")
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car Car() {
        return new Car();
    }
}
