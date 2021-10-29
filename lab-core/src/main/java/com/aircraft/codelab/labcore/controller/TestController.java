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

package com.aircraft.codelab.labcore.controller;

import com.aircraft.codelab.cache.service.RedisService;
import com.aircraft.codelab.core.entities.CommonResult;
import com.aircraft.codelab.core.enums.ResultCode;
import com.aircraft.codelab.core.service.DatePattern;
import com.aircraft.codelab.core.util.DateUtil;
import com.aircraft.codelab.core.util.JsonUtil;
import com.aircraft.codelab.labcore.pojo.entity.UserDO;
import com.aircraft.codelab.labcore.pojo.vo.UserVO;
import com.aircraft.codelab.labcore.service.UserConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 2020-11-03
 * test
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@RestController
@Api(tags = "测试")
@RequestMapping("/hello")
public class TestController {
    private static final HashMap<String, Object> INFO;

    static {
        INFO = new HashMap<>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
    }

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisService redisService;

    @ApiOperation(value = "redis测试", notes = "redis hash")
    @GetMapping("/redis")
    public CommonResult<Map<Object, Object>> helloRedis(@RequestParam(defaultValue = "0", name = "id") Long parentId) {
        log.debug("redis test");
        INFO.put("id", parentId);
        redisTemplate.opsForHash().putAll("test:map:2", INFO);
        Map<Object, Object> map = redisTemplate.opsForHash().entries("test:map:2");
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), map);
    }

    @ApiOperation(value = "redis序列化测试")
    @GetMapping("/serializer")
    public CommonResult<UserDO> helloRedisSerializer() {
        log.debug("redis test");
        String key = DateUtil.getDateTimeNow(DatePattern.PURE_DATETIME_PATTERN);
        UserDO user = UserDO.builder()
                .name("zhang")
                .id(10000L)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();
        // 转json存储
        String s = JsonUtil.toJsonString(user);
        redisService.set(key, s);
        Object o = redisService.get(key);
        UserDO userDO = JsonUtil.jsonToObject(o.toString(), UserDO.class);
        UserDO userDO1 = JsonUtil.jsonToObject(o.toString(), new TypeReference<UserDO>() {
        });
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), userDO);
    }

    @ApiOperation(value = "mapstruct测试")
    @GetMapping("/mapstruct")
    public CommonResult<UserDO> helloMapstruct() {
        log.debug("mapstruct test");
        UserVO userVO = UserVO.builder().id(100L).name("zhang").build();
        UserDO userDO = UserConverter.INSTANCE.vo2do(userVO);
        UserDO build = userDO.toBuilder()
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), build);
    }

    @Resource(name = "mailThreadPoolExecutor")
    private ThreadPoolExecutor executor;

    @GetMapping(value = "/pool", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> pool() {
        log.debug("============================");
        for (int i = 0; i < 50; i++) {
            int task = i + 1;
            executor.execute(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("taskNo:{},threadName:{}", task, Thread.currentThread().getName());
            });
        }
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }

    @GetMapping(value = "/poolStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> poolStatus() {
        int poolSize = executor.getPoolSize();
        int activeCount = executor.getActiveCount();
        long completedTaskCount = executor.getCompletedTaskCount();
        int queueSize = executor.getQueue().size();
        log.debug("poolSize:{},activeCount:{},completedTaskCount:{},queueSize:{}", poolSize, activeCount, completedTaskCount, queueSize);
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }
}
