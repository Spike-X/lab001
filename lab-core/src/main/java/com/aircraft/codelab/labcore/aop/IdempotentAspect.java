package com.aircraft.codelab.labcore.aop;

import cn.hutool.crypto.digest.DigestUtil;
import com.aircraft.codelab.cache.config.RedisConfig;
import com.aircraft.codelab.core.entities.CommonResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 2021-12-22
 *
 * @author tao.zhang
 * @since 1.0
 */
@Aspect
@Component
@Slf4j
@ConditionalOnClass(RedisConfig.class)
public class IdempotentAspect {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(idempotent)")
    public Object noRepeatSubmit(ProceedingJoinPoint proceedingJoinPoint, Idempotent idempotent) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 生产使用请求头token作为id
        String sessionId = Objects.requireNonNull(attributes).getSessionId();
        log.debug("sessionId: {}", sessionId);
        String requestUri = request.getRequestURI();
        log.debug("requestUri: {}", requestUri);
        Object[] args = proceedingJoinPoint.getArgs();
        String param = JSON.toJSONString(args);
        // md5摘要
        String key = DigestUtil.md5Hex(sessionId + param);
        log.debug("key: {}", key);
        String redisKey = String.format("idempotent:%s", key);
        Boolean success = setIfAbsent(redisKey, idempotent.timeout(), idempotent.timeUnit());
        if (success) {
            return proceedingJoinPoint.proceed();
        }
        return CommonResult.success(idempotent.message());
    }

    public Boolean setIfAbsent(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, "", timeout, timeUnit);
    }
}
