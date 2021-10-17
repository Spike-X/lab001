package com.aircraft.codelab.labcore.proxy;

import com.aircraft.codelab.labcore.aop.MathCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

/**
 * 2021-10-06
 *
 * @author tao.zhang
 * @since 1.0
 */
@SpringBootTest
public class AopTest {
    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void test01() {
        MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
        String div = mathCalculator.div(3, 1);
    }
}
