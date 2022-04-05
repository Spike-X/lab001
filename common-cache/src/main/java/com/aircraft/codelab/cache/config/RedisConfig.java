package com.aircraft.codelab.cache.config;

import com.aircraft.codelab.cache.service.RedisService;
import com.aircraft.codelab.cache.service.impl.RedisServiceImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * 2020-11-03
 * Redis配置类
 *
 * @author tao.zhang
 * @since 1.0
 */
//@EnableCaching
@Configuration
public class RedisConfig {
    @Resource
    private RedisProperties redisProperties;

    @Bean
    @ConditionalOnProperty(prefix = "spring.redis", name = "cluster.nodes")
    public RedissonClient clusterRedisson() {
        String[] clusterArray = redisProperties.getCluster().getNodes().stream()
                .map(node -> node.startsWith("redis://") ? node : "redis://" + node)
                .toArray(String[]::new);
        Config config = new Config();
        //用"rediss://"来启用SSL连接
        config.useClusterServers()
                .addNodeAddress(clusterArray)
                .setPassword(redisProperties.getPassword());
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.redis", name = "sentinel.nodes")
    public RedissonClient sentinelRedisson() {
        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        String[] sentinelArray = sentinel.getNodes().stream()
                .map(node -> node.startsWith("redis://") ? node : "redis://" + node)
                .toArray(String[]::new);
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName(sentinel.getMaster())
                .addSentinelAddress(sentinelArray)
                .setPassword(redisProperties.getPassword());
        return Redisson.create(config);
    }

    @Lazy
    @Bean
    @ConditionalOnMissingBean(name = {"clusterRedisson", "sentinelRedisson"})
    public RedissonClient singleRedisson() {
        String host = redisProperties.getHost();
        //用"rediss://"来启用SSL连接
        host = host.startsWith("redis://") ? host : "redis://" + host;
        int port = redisProperties.getPort();
        String address = host + ":" + port;
        Config config = new Config();
        config.useSingleServer()
                .setAddress(address)
                .setPassword(redisProperties.getPassword());
        return Redisson.create(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        RedisSerializer<Object> serializer = redisSerializer();

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(serializer);
        return redisTemplate;
    }

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    @Bean
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory redisConnectionFactory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()))
                .disableCachingNullValues();
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }

    @Bean
    public RedisService redisService() {
        return new RedisServiceImpl();
    }
}
