package com.aircraft.codelab.labcore.util;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author cube.li
 * @date 2021/10/1 23:01
 * @description Redis工具类
 */
public class RedisUtils {

    private static RedisTemplate<String, Object> redisTemplate;

    private RedisUtils() {
        throw new UnsupportedOperationException("construct RedisUtils is not allowed");
    }

    public static void init(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public static void set(String key, Object value, long expire) {
        set(key, value, expire, TimeUnit.SECONDS);
    }

    public static boolean delete(String key) {
        return BooleanUtils.isTrue(redisTemplate.delete(key));
    }

    /**
     * 根据key集合批量删除
     *
     * @param keys key的集合
     * @return 成功删除的个数
     */
    public static long delete(Collection<String> keys) {
        Long ret = redisTemplate.delete(keys);
        return ret == null ? 0 : ret;
    }

    public static boolean hasKey(String key) {
        return BooleanUtils.isTrue(redisTemplate.hasKey(key));
    }
}
