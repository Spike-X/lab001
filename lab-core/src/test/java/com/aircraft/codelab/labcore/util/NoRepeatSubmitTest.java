package com.aircraft.codelab.labcore.util;

import com.aircraft.codelab.labcore.aop.NoRepeatSubmit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 2022-05-15
 *
 * @author tao.zhang
 * @since 1.0
 */
@SpringBootTest
public class NoRepeatSubmitTest {
    @Resource
    private NoRepeatSubmit noRepeatSubmit;

    @Test
    void test() {
        noRepeatSubmit.test();
    }
}
