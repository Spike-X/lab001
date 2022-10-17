package com.aircraft.codelab.pioneer.spring;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 2022-07-14
 *
 * @author tao.zhang
 * @since 1.0
 */
public class LifeCycleTest {
    @Test
    public void test01() {
        // 创建容器
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(LifeCycle.class);

//        applicationContext.getBean("");
        // 关闭容器
        applicationContext.close();
    }
}
