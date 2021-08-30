package com.aircraft.codelab.cache.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 2021-01-03
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface RedisService {
    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return Boolean
     */
    Boolean hasKey(String key);

    /**
     * 设置key过期时间
     *
     * @param key  键
     * @param time 过期时间
     * @return Boolean
     */
    Boolean expire(String key, long time);

    /**
     * 获取key过期时间
     *
     * @param key 键
     * @return Long
     */
    Long getExpire(String key);

    /**
     * 删除指定Key
     *
     * @param key 键
     * @return Boolean
     */
    Boolean delete(String key);

    /**
     * 批量删除指定Key
     *
     * @param keys 键
     * @return Long
     */
    Long delete(List<String> keys);

    /**
     * 添加String类型 K,V
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, Object value);

    /**
     * 添加String类型 K,V
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间(秒)
     */
    void set(String key, Object value, long time);

    /**
     * 获取String类型 K,V
     *
     * @param key 键
     * @return Object
     */
    Object get(String key);


    /**
     * 获取Hash结构中的属性
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     */
    void hSetAll(String key, Map<String, ?> map);

    /**
     * 删除Hash结构中的属性
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     */
    Long hDecr(String key, String hashKey, Long delta);


    /**
     * 获取Set结构
     */
    Set<Object> sMembers(String key);

    /**
     * 向Set结构中添加属性
     */
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     */
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     */
    Long sRemove(String key, Object... values);


    /**
     * 获取List结构中的属性
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     */
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     */
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加属性
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     */
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * 从List结构中移除属性
     */
    Long lRemove(String key, long count, Object value);
}
