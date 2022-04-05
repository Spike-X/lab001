package com.aircraft.codelab.labcore.redis;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.util.List;

/**
 * 2022-04-05
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface IGeoService {
    /**
     * 把城市信息保存到 Redis 中
     *
     * @param cityInfos {@link CityInfo}
     * @return 成功保存的个数
     */
    Long batchAddCity(List<CityInfo> cityInfos);

    /**
     * 删除指定城市
     *
     * @param cityIds 城市编码
     * @return 成功删除的个数
     */
    Long batchRemoveCity(String... cityIds);

    /**
     * <h2>获取给定城市的坐标</h2>
     *
     * @param cities 给定城市 key
     * @return {@link Point}s
     */
    List<Point> getCityPos(String[] cities);

    /**
     * <h2>获取两个城市之间的距离</h2>
     *
     * @param city1  第一个城市
     * @param city2  第二个城市
     * @param metric {@link Metric} 单位信息, 可以是 null
     * @return {@link Distance}
     */
    Distance getTwoCityDistance(String city1, String city2, Metric metric);

    /**
     * <h2>根据给定地理位置坐标获取指定范围内的地理位置集合</h2>
     *
     * @param within {@link Circle} 中心点和距离
     * @param args   {@link RedisGeoCommands.GeoRadiusCommandArgs} 限制返回的个数和排序方式, 可以是 null
     * @return {@link RedisGeoCommands.GeoLocation}
     */
    GeoResults<RedisGeoCommands.GeoLocation<String>> getPointRadius(Circle within, RedisGeoCommands.GeoRadiusCommandArgs args);

    /**
     * <h2>根据给定地理位置获取指定范围内的地理位置集合</h2>
     *
     * @param member
     * @param distance
     * @param args
     * @return {@link RedisGeoCommands.GeoLocation}
     */
    GeoResults<RedisGeoCommands.GeoLocation<String>> getMemberRadius(String member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args);

    /**
     * <h2>获取某个地理位置的 geohash 值</h2>
     *
     * @param cities 给定城市 key
     * @return city geohashs
     */
    List<String> getCityGeoHash(String[] cities);
}
