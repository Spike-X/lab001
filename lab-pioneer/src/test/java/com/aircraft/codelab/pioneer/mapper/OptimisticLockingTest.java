package com.aircraft.codelab.pioneer.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 2021-09-23
 *
 * @author tao.zhang
 * @since 1.0
 */
@SpringBootTest
public class OptimisticLockingTest {
    @Resource
    private ProductMapper productMapper;

    @Test
    void concurrentTest() {

    }
}
