package com.aircraft.codelab.pioneer.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2022-04-05
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service
public class GeoServiceImpl implements IGeoService {
    private final String GEO_KEY = "ah-cities";

    /**
     * redis 客户端
     */
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public GeoServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Long batchAddCity(List<CityInfo> cityInfos) {
        Map<String, Point> points = new HashMap<>(16);
        cityInfos.forEach(ci -> points.put(ci.getCityName(), new Point(ci.getLongitude(), ci.getLatitude())));
        return stringRedisTemplate.boundGeoOps(GEO_KEY).add(points);
    }

    @Override
    public Long batchRemoveCity(String... cityInfos) {
        return stringRedisTemplate.boundGeoOps(GEO_KEY).remove(cityInfos);
    }

    @Override
    public List<Point> getCityPos(String[] cities) {
        GeoOperations<String, String> ops = stringRedisTemplate.opsForGeo();
        return ops.position(GEO_KEY, cities);
    }

    @Override
    public Distance getTwoCityDistance(String city1, String city2, Metric metric) {
        GeoOperations<String, String> ops = stringRedisTemplate.opsForGeo();
        return metric == null ?
                ops.distance(GEO_KEY, city1, city2) : ops.distance(GEO_KEY, city1, city2, metric);
    }

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<String>> getPointRadius(Circle within, RedisGeoCommands.GeoRadiusCommandArgs args) {
        GeoOperations<String, String> ops = stringRedisTemplate.opsForGeo();
        return args == null ?
                ops.radius(GEO_KEY, within) : ops.radius(GEO_KEY, within, args);
    }

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<String>> getMemberRadius(
            String member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args) {
        GeoOperations<String, String> ops = stringRedisTemplate.opsForGeo();
        return args == null ?
                ops.radius(GEO_KEY, member, distance) : ops.radius(GEO_KEY, member, distance, args);
    }

    @Override
    public List<String> getCityGeoHash(String[] cities) {
        GeoOperations<String, String> ops = stringRedisTemplate.opsForGeo();
        return ops.hash(GEO_KEY, cities);
    }
}
