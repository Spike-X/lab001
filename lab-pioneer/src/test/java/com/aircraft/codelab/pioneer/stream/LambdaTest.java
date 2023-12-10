package com.aircraft.codelab.pioneer.stream;

import com.aircraft.codelab.pioneer.pojo.entity.UserDO;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 2022-10-19
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class LambdaTest {
    @Test
    void tt() {
        List<UserDO> userDOArrayList = new ArrayList<>();
        UserDO userDO1 = new UserDO();
        userDO1.setPassword("1");
        UserDO userDO2 = new UserDO();
        userDO2.setId(2L);
        userDO2.setPassword("2");
        UserDO userDO3 = new UserDO();
        userDO3.setId(3L);
        userDO3.setPassword("3");
        UserDO userDO4 = new UserDO();
        userDO4.setPassword("4");
        userDOArrayList.add(userDO1);
        userDOArrayList.add(userDO2);
        userDOArrayList.add(userDO3);
        userDOArrayList.add(userDO4);
        Map<Long, UserDO> collectItemDtoMap = userDOArrayList.stream()
                .collect(Collectors.toMap(UserDO::getId, Function.identity(), (key1, key2) -> key2));

        List<String> nonExistentList = new ArrayList<>();
        nonExistentList.add("111");
        nonExistentList.add("222");
        String nonExistent = String.join(",", nonExistentList);
        System.out.println(nonExistent);
    }

    @Test
    void beanTest() {
        /*UserDO userDO = UserDO.builder().name("111").password("aaa").build();
        User user = new User().setName("222").setPassword("bbb");
        BeanUtils.copyProperties(userDO, user);
        log.debug("userDO :{}", userDO);
        log.debug("user :{}", user);
        userDO.setName("333");
        log.debug("userDO :{}", userDO);
        log.debug("user :{}", user);*/

        Map<String, Object> map = new HashMap<>();
        map.put("1", "1");
        Object o = map.get("2");

        List<String> list = new ArrayList<>();
        list.add(null);
        list.add("1");
        List<String> collect = list.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(collect)) {
            System.out.println("===>");
        }

        UserDO userDO = UserDO.builder().id(1L).name("111").password("aaa").build();
        ArrayList<UserDO> arrayList = Lists.newArrayList(userDO);
        Map<Long, UserDO> userDOMap = arrayList.stream().collect(Collectors.toMap(UserDO::getId, Function.identity(), (key1, key2) -> key2));
        UserDO userDO1 = userDOMap.get(1L);
        userDO1.setId(2L);
        log.debug("userDO: {}", JSON.toJSONString(userDO));
        log.debug("userDO1: {}", JSON.toJSONString(userDO1));
    }
}
