package com.aircraft.codelab.pioneer.redis;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 2022-04-05
 *
 * @author tao.zhang
 * @since 1.0
 */
@SpringBootTest
public class GeoServiceTest {
    /**
     * fake some cityInfos
     */
    private List<CityInfo> cityInfos;

    @Autowired
    private IGeoService geoService;

    @BeforeEach
    public void init() {
        cityInfos = new ArrayList<>();
        cityInfos.add(new CityInfo("hefei", 117.17, 31.52));
        cityInfos.add(new CityInfo("anqing", 117.02, 30.31));
        cityInfos.add(new CityInfo("huaibei", 116.47, 33.57));
        cityInfos.add(new CityInfo("suzhou", 116.58, 33.38));
        cityInfos.add(new CityInfo("fuyang", 115.48, 32.54));
        cityInfos.add(new CityInfo("bengbu", 117.21, 32.56));
        cityInfos.add(new CityInfo("huangshan", 118.18, 29.43));
    }

    /**
     * <h2>测试 saveCityInfoToRedis 方法</h2>
     */
    @Test
    public void testBatchAddCity() {
        System.out.println(geoService.batchAddCity(cityInfos));
    }

    @Test
    public void testBatchRemoveCity() {
        System.out.println(geoService.batchRemoveCity(cityInfos.get(2).getCityName()));
    }

    /**
     * <h2>测试 getCityPos 方法</h2>
     * 如果传递的 city 在 Redis 中没有记录, 会返回什么呢 ? 例如, 这里传递的 xxx
     */
    @Test
    public void testGetCityPos() {
        System.out.println(JSON.toJSONString(geoService.getCityPos(
                Arrays.asList("anqing", "suzhou", "xxx").toArray(new String[3]))));
    }

    /**
     * <h2>测试 getTwoCityDistance 方法</h2>
     */
    @Test
    public void testGetTwoCityDistance() {
        System.out.println(geoService.getTwoCityDistance("anqing", "suzhou", null).getValue());
        System.out.println(geoService.getTwoCityDistance("anqing", "suzhou", Metrics.KILOMETERS).getValue());
    }

    /**
     * <h2>测试 getPointRadius 方法</h2>
     */
    @Test
    public void testGetPointRadius() {
        Point center = new Point(cityInfos.get(0).getLongitude(), cityInfos.get(0).getLatitude());
        Distance radius = new Distance(200, Metrics.KILOMETERS);
        Circle within = new Circle(center, radius);

        System.out.println(JSON.toJSONString(geoService.getPointRadius(within, null)));

        // order by 距离 limit 2, 同时返回距离中心点的距离
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands
                .GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeCoordinates()
                .includeDistance()
                .sortAscending().limit(2);
        System.out.println(JSON.toJSONString(geoService.getPointRadius(within, args)));
    }

    /**
     * <h2>测试 getMemberRadius 方法</h2>
     */
    @Test
    public void testGetMemberRadius() {
        Distance radius = new Distance(200, Metrics.KILOMETERS);
        System.out.println(JSON.toJSONString(geoService.getMemberRadius("suzhou", radius, null)));
        // order by 距离 limit 2, 同时返回距离中心点的距离
        RedisGeoCommands.GeoRadiusCommandArgs args =
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().limit(2).sortAscending();
        System.out.println(JSON.toJSONString(geoService.getMemberRadius("suzhou", radius, args)));
    }

    /**
     * <h2>测试 getCityGeoHash 方法</h2>
     */
    @Test
    public void testGetCityGeoHash() {
        System.out.println(JSON.toJSONString(geoService.getCityGeoHash(
                Arrays.asList("anqing", "suzhou", "xxx").toArray(new String[3]))));
    }
}
