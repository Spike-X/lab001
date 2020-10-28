package com.zt_wmail500.demo;

import org.junit.jupiter.api.*;

/**
 * @program: demo
 * @description: JUnit5测试
 * @author: tao.zhang
 * @create: 2020-07-26 18:09
 **/
@DisplayName("我的第一个测试用例")
public class JUnit5Test {

    @BeforeAll
    public static void init() {
        System.out.println("初始化数据");
    }

    @AfterAll
    public static void cleanup() {
        System.out.println("清理数据");
    }

    @BeforeEach
    public void tearUp() {
        System.out.println("当前测试方法开始");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("当前测试方法结束");
    }

    @DisplayName("我的第一个测试")
    @Test
    void testFirstTest() {
        System.out.println("我的第一个测试开始测试");
    }

    @DisplayName("我的第二个测试")
    @Test
    void testSecondTest() {
        System.out.println("我的第二个测试开始测试");
    }

}
