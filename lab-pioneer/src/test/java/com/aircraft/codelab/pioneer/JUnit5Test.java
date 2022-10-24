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

package com.aircraft.codelab.pioneer;

import com.aircraft.codelab.pioneer.pojo.Menu;
import com.aircraft.codelab.pioneer.pojo.vo.UserVo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @DisplayName("Json序列化测试")
    @Test
    void testFirstTest() {
        try {
            System.out.println("我的第一个测试开始测试");
            List<String> stringList = Lists.newArrayList("Blackberry", null, "Avocado", "Cherry", "Apricots");

            List<Integer> numList = Lists.newArrayList(1, null, 3, 4, 5);

            UserVo userVo1 = UserVo.builder().username("1").build();
            UserVo userVo2 = UserVo.builder().id(2L).build();
            UserVo userVo3 = UserVo.builder().id(3L).username("3").build();
            List<UserVo> objectList = Lists.newArrayList(userVo1, userVo2, userVo3);
            String s = testException();
            log.debug("stringList: {}", stringList);
            log.debug("stringList: {}", JSON.toJSONString(stringList));
            log.debug("numList: {}", numList);
            log.debug("numList: {}", JSON.toJSONString(numList));
            log.debug("objectList: {}", objectList);
            log.debug("objectList: {}", JSON.toJSONString(objectList));
            log.debug("objectList: {}", JSON.toJSONString(objectList, SerializerFeature.WriteMapNullValue));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.debug("end");
    }

    private String testException() {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @DisplayName("遍历树形结构测试")
    @Test
    void testSecondTest() {
        //模拟从数据库查询出来
        List<Menu> menus = Lists.newArrayList(new Menu(1L, "根节点", null),
                new Menu(2L, "子节点1", 1L),
                new Menu(3L, "子节点1.1", 2L),
                new Menu(4L, "子节点1.2", 2L),
                new Menu(5L, "子节点1.3", 2L),
                new Menu(6L, "根节点2", 1L),
                new Menu(7L, "根节点2.1", 6L),
                new Menu(8L, "根节点2.2", 6L),
                new Menu(9L, "根节点2.2.1", 7L),
                new Menu(10L, "根节点2.2.2", 7L),
                new Menu(11L, "根节点3", 1L),
                new Menu(12L, "根节点3.1", 11L));

        //获取父节点
        // peek对一个对象进行操作的时候,对象不变,但是可以改变对象里面的值
        List<Menu> collect = menus.stream().filter(m -> Objects.equals(0L, m.getParentId()) || Objects.equals(null, m.getParentId()))
                .peek(m -> m.setChildList(getChildren(m, menus))).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect, SerializerFeature.SortField, SerializerFeature.WriteMapNullValue));
    }

    /**
     * 递归查询子节点
     *
     * @param root 根节点
     * @param all  所有节点
     * @return 根节点信息
     */
    private List<Menu> getChildren(Menu root, List<Menu> all) {
        return all.stream().filter(m -> Objects.equals(m.getParentId(), root.getId()))
                .map((m) -> {
                    m.setChildList(getChildren(m, all));
                    return m;
                }).collect(Collectors.toList());
    }
}
