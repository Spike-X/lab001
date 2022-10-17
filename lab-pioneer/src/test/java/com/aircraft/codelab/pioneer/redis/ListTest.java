package com.aircraft.codelab.pioneer.redis;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 2022-04-06
 *
 * @author tao.zhang
 * @since 1.0
 */
@SpringBootTest
public class ListTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test1() {
        CityInfo cityInfo1 = CityInfo.builder().cityName("hefei").longitude(117.17).latitude(31.52).build();
        CityInfo cityInfo2 = CityInfo.builder().cityName("suzhou").longitude(116.58).latitude(33.38).build();
        CityInfo cityInfo3 = CityInfo.builder().cityName("anqing").longitude(117.02).latitude(30.31).build();
        List<String> cityInfoList = new ArrayList<>();
        cityInfoList.add(JSON.toJSONString(cityInfo1));
        cityInfoList.add(JSON.toJSONString(cityInfo2));
        cityInfoList.add(JSON.toJSONString(cityInfo3));
        stringRedisTemplate.opsForList().leftPushAll("ops-list", cityInfoList);
    }

    @Test
    public void test2() {
        List<String> range = stringRedisTemplate.opsForList().range("ops-list", 0, -1);
        System.out.println(range);
        long currentPage = 1;
        long pageSize = 2;
        List<String> range1 = stringRedisTemplate.opsForList()
                .range("ops-list", (currentPage - 1) * pageSize, currentPage * pageSize - 1);
        System.out.println(range1);
    }
}
