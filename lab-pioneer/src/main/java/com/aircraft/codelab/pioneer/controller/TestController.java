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

package com.aircraft.codelab.pioneer.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.extra.servlet.ServletUtil;
import com.aircraft.codelab.cache.service.RedisService;
import com.aircraft.codelab.core.entities.CommonResult;
import com.aircraft.codelab.core.enums.ResultCode;
import com.aircraft.codelab.core.util.DateUtil;
import com.aircraft.codelab.core.util.JsonUtil;
import com.aircraft.codelab.pioneer.aop.Idempotent;
import com.aircraft.codelab.pioneer.async.thread.ThreadService;
import com.aircraft.codelab.pioneer.pojo.entity.UserDO;
import com.aircraft.codelab.pioneer.pojo.vo.SysMenuCreatVo;
import com.aircraft.codelab.pioneer.pojo.vo.UserVO;
import com.aircraft.codelab.pioneer.service.ProductService;
import com.aircraft.codelab.pioneer.service.UserConverter;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ProductService productService;

    @ApiOperation(value = "lock", notes = "乐观锁")
    @GetMapping("/lock")
    public CommonResult<?> lock() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            try {
                countDownLatch.await();
                int row = productService.updatePrice(new BigDecimal("30.00"), 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "lock1").start();
        countDownLatch.countDown();
        new Thread(() -> {
            try {
                countDownLatch.await();
                int row = productService.updatePrice(new BigDecimal("20.00"), -1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "lock2").start();
        countDownLatch.countDown();
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }

    @Resource
    private RedisService redisService;

    @ApiOperation(value = "redis序列化测试")
    @GetMapping("/serializer")
    public CommonResult<UserDO> redisSerializer() {
        log.debug("redis test");
        String key = DateUtil.getDateTime(DatePattern.PURE_DATETIME_PATTERN);
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
    public CommonResult<UserDO> mapstruct() {
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
    public CommonResult<?> threadPool() {
        log.debug("=====>");
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

    @Resource
    private WebServerApplicationContext webServerApplicationContext;

    @GetMapping(value = "/tomcat/actuator", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> tomcatStatus() {
        TomcatWebServer webServer = (TomcatWebServer) webServerApplicationContext.getWebServer();
        //获取webServer线程池
        org.apache.tomcat.util.threads.ThreadPoolExecutor executor = (org.apache.tomcat.util.threads.ThreadPoolExecutor) (webServer)
                .getTomcat()
                .getConnector()
                .getProtocolHandler()
                .getExecutor();
        Map<String, String> returnMap = new LinkedHashMap<>();
        returnMap.put("核心线程数", String.valueOf(executor.getCorePoolSize()));
        returnMap.put("最大线程数", String.valueOf(executor.getMaximumPoolSize()));
        returnMap.put("活跃线程数", String.valueOf(executor.getActiveCount()));
        returnMap.put("池中当前线程数", String.valueOf(executor.getPoolSize()));
        returnMap.put("历史最大线程数", String.valueOf(executor.getLargestPoolSize()));
        returnMap.put("线程允许空闲时间/s", String.valueOf(executor.getKeepAliveTime(TimeUnit.SECONDS)));
        returnMap.put("核心线程数是否允许被回收", String.valueOf(executor.allowsCoreThreadTimeOut()));
        returnMap.put("提交任务总数", String.valueOf(executor.getSubmittedCount()));
        returnMap.put("历史执行任务的总数(近似值)", String.valueOf(executor.getTaskCount()));
        returnMap.put("历史完成任务的总数(近似值)", String.valueOf(executor.getCompletedTaskCount()));
        returnMap.put("工作队列任务数量", String.valueOf(executor.getQueue().size()));
        returnMap.put("拒绝策略", executor.getRejectedExecutionHandler().getClass().getSimpleName());
        log.debug("returnMap: {}", JSON.toJSONString(returnMap));
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), returnMap);
    }

    @Resource
    private ThreadService threadService;

    @GetMapping(value = "/cas", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> casTest() {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    int increment = threadService.getAndIncrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "cas" + i).start();
            countDownLatch.countDown();
        }
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }

    @Idempotent
    @PostMapping(value = "/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> repeatSubmit(@RequestBody SysMenuCreatVo sysMenuCreatVo) {
        log.debug("submit =====>");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = Objects.requireNonNull(attributes).getResponse();
        try {
            int a = 1000 / 0;
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            log.error("error reason : {}", e.getMessage(), e);
        }
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }

    @PostMapping(value = "/requestBody", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> testRequest(@RequestBody List<Long> idList,
                                       @RequestParam("ids") List<Long> ids,
                                       @RequestParam("dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTime) {
        log.debug("idList: {}", idList);
        log.debug("ids: {}", ids);
        log.debug("dateTime: {}", dateTime);
        try {
            int a = 1000 / 0;
        } catch (Exception e) {
            log.error("error reason : {}", e.getMessage(), e);
        }
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }

    @GetMapping(value = "/ip", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> ipTest() throws UnknownHostException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        StringBuffer requestURL = request.getRequestURL();
        String requestURI = request.getRequestURI();
        log.debug("requestURL: {}", requestURL);
        log.debug("requestURI: {}", requestURI);

        int localPort = request.getLocalPort();
        InetAddress localHost = InetAddress.getLocalHost();
        String hostName = localHost.getHostName();
        String hostAddress = localHost.getHostAddress();
        log.debug("hostName: {}", hostName);
        log.debug("windows本机ip:port {}", hostAddress + ":" + localPort);
        String clientIP = ServletUtil.getClientIP(request);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), clientIP);
    }
}
