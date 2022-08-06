/*
 * Copyright (c) 2020, Tao Zhang (zt_wmail500@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aircraft.codelab.labcore;

import com.aircraft.codelab.labcore.pojo.vo.UserVO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.List;

/**
 * 2020-10-31
 * JUnit5测试
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
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
        List<String> stringList = Lists.newArrayList("Blackberry", null, "Avocado", "Cherry", "Apricots");

        List<Integer> numList = Lists.newArrayList(1, null, 3, 4, 5);

        UserVO userVO1 = UserVO.builder().name("1").build();
        UserVO userVO2 = UserVO.builder().id(2L).build();
        UserVO userVO3 = UserVO.builder().id(3L).name("3").build();
        List<UserVO> objectList = Lists.newArrayList(userVO1, userVO2, userVO3);

        log.debug("stringList: {}", stringList);
        log.debug("stringList: {}", JSON.toJSONString(stringList));
        log.debug("numList: {}", numList);
        log.debug("numList: {}", JSON.toJSONString(numList));
        log.debug("objectList: {}", objectList);
        log.debug("objectList: {}", JSON.toJSONString(objectList));
        log.debug("objectList: {}", JSON.toJSONString(objectList, SerializerFeature.WriteMapNullValue));
    }

    @DisplayName("我的第二个测试")
    @Test
    void testSecondTest() {
        System.out.println("我的第二个测试开始测试");
    }
}
