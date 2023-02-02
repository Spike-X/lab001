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
import com.aircraft.codelab.core.exception.ApiException;
import com.aircraft.codelab.core.util.DateUtil;
import com.aircraft.codelab.core.util.JsonUtil;
import com.aircraft.codelab.core.util.ValidateList;
import com.aircraft.codelab.core.util.ValidateUtil;
import com.aircraft.codelab.pioneer.async.thread.ThreadService;
import com.aircraft.codelab.pioneer.pojo.entity.UserDO;
import com.aircraft.codelab.pioneer.pojo.vo.CreatOrderVo;
import com.aircraft.codelab.pioneer.pojo.vo.UserVo;
import com.aircraft.codelab.pioneer.service.ForestClient;
import com.aircraft.codelab.pioneer.service.OpenFeignService;
import com.aircraft.codelab.pioneer.service.ProductService;
import com.aircraft.codelab.pioneer.service.UserConverter;
import com.aircraft.codelab.pioneer.spring.PlaceOrderEvent;
import com.aircraft.codelab.pioneer.spring.PlaceOrderEventMessage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dtflys.forest.exceptions.ForestNetworkException;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
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
@Api(tags = "单元测试")
@RequestMapping("/test")
public class TestController {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("#{'${collection.bank.itemId:}'}")
    private List<Long> itemId;

    @Value("#{${collection.bank.itemSize:{}}}")
    private Map<String, String> itemAndSize;

    @Resource
    private ProductService productService;

    @ApiOperation(value = "乐观锁测试", notes = "乐观锁")
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
        UserDO userDO1 = JsonUtil.jsonToObject(o.toString(), new com.fasterxml.jackson.core.type.TypeReference<UserDO>() {
        });
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), userDO);
    }

    @ApiOperation(value = "mapstruct测试")
    @GetMapping("/mapstruct")
    public CommonResult<UserDO> mapstruct() {
        log.debug("mapstruct test");
        UserVo userVO = UserVo.builder().id(100L).username("zhang").build();
        UserDO userDO = UserConverter.INSTANCE.vo2do(userVO);
        UserDO build = userDO.toBuilder()
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), build);
    }

    @Resource(name = "mailThreadPoolExecutor")
    private ThreadPoolExecutor executor;

    @ApiOperation(value = "线程池测试")
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

    @ApiOperation(value = "线程池状态监控测试")
    @GetMapping(value = "/poolStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> poolStatus() {
        int poolSize = executor.getPoolSize();
        int activeCount = executor.getActiveCount();
        long completedTaskCount = executor.getCompletedTaskCount();
        int queueSize = executor.getQueue().size();
        log.debug("poolSize:{},activeCount:{},completedTaskCount:{},queueSize:{}", poolSize, activeCount, completedTaskCount, queueSize);
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }

    @Autowired(required = false)
    private WebServerApplicationContext webServerApplicationContext;

    @ApiOperation(value = "Tomcat状态测试")
    @GetMapping(value = "/tomcat/actuator", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<Map<String, Object>> tomcatStatus() {
        TomcatWebServer webServer = (TomcatWebServer) webServerApplicationContext.getWebServer();
        //获取webServer线程池
        org.apache.tomcat.util.threads.ThreadPoolExecutor executor = (org.apache.tomcat.util.threads.ThreadPoolExecutor) (webServer)
                .getTomcat()
                .getConnector()
                .getProtocolHandler()
                .getExecutor();
        Map<String, Object> returnMap = new LinkedHashMap<>();
        returnMap.put("核心线程数", executor.getCorePoolSize());
        returnMap.put("最大线程数", executor.getMaximumPoolSize());
        returnMap.put("活跃线程数", executor.getActiveCount());
        returnMap.put("池中当前线程数", executor.getPoolSize());
        returnMap.put("历史最大线程数", executor.getLargestPoolSize());
        returnMap.put("线程允许空闲时间/s", executor.getKeepAliveTime(TimeUnit.SECONDS));
        returnMap.put("核心线程数是否允许被回收", executor.allowsCoreThreadTimeOut());
        returnMap.put("提交任务总数", executor.getSubmittedCount());
        returnMap.put("历史执行任务的总数(近似值)", executor.getTaskCount());
        returnMap.put("历史完成任务的总数(近似值)", executor.getCompletedTaskCount());
        returnMap.put("工作队列任务数量", executor.getQueue().size());
        returnMap.put("拒绝策略", executor.getRejectedExecutionHandler().getClass().getSimpleName());
        log.debug("returnMap: {}", JSON.toJSONString(returnMap));
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), returnMap);
    }

    @Resource
    private ThreadService threadService;

    @ApiOperation(value = "自旋锁测试")
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

    //    @Idempotent
    @ApiOperation(value = "属性校验测试1")
    @PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> validate(@RequestBody CreatOrderVo<?> creatOrderVo) {
        log.debug("validate =====>");
        Object dx = creatOrderVo.getDx();
        String jsonString = JSON.toJSONString(dx);
        UserDO userDO = JSONObject.parseObject(jsonString, UserDO.class);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = Objects.requireNonNull(attributes).getResponse();
        try {
            // 最佳实践：
            // String -> @NotBlank + @Length
            // List -> @NotEmpty + @Size
            // 对象嵌套对象 || 对象嵌套对象集合 -> @NotNull + @Valid || @NotEmpty + @Size + @Valid (无法校验List<String>,List<包装类型>)
            // 数字类型 Long,Integer -> @NotNull + @Range || BigDecimal -> @NotNull + @Digits + @DecimalMin || 基本类型 @Range
            // 银行卡，信用卡卡号 -> @NotBlank + @CreditCardNumber
            // 邮件地址 -> @NotBlank + @Email
            // 日期时间 -> @NotNull + @Past||@Future
            ValidateUtil.validate(creatOrderVo);
        } catch (ConstraintViolationException e) {
            log.error("parameter validation failed : {}", e.getMessage(), e);
            return CommonResult.success(ResultCode.SUCCESS.getMessage(), e.getMessage());
        }
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }

    @ApiOperation(value = "属性校验测试2")
    @PostMapping(value = "/validateList", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> validateList(@RequestBody @Valid ValidateList<UserVo> ids) {
        log.debug("validateList =====>");
        List<UserVo> list = ids.getList();
        list.get(0).setTaskList(new ArrayList<>());
        ValidateList<UserVo> userVoList = new ValidateList<>(list);
        ValidateUtil.validate(userVoList);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), list);
    }

    @ApiOperation(value = "requestBody测试")
    @PostMapping(value = "/requestBody", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> testRequest(@RequestBody List<Long> idList,
                                       @RequestParam("ids") List<Long> ids,
                                       @RequestParam("dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTime) {
        log.debug("idList: {}", idList);
        log.debug("ids: {}", ids);
        log.debug("dateTime: {}", dateTime);
        if (CollectionUtils.isNotEmpty(itemId)) {
            itemId.forEach(id -> log.debug("id: {}", id));
        }
        if (MapUtils.isNotEmpty(itemAndSize)) {
            for (Map.Entry<String, String> next : itemAndSize.entrySet()) {
                String key = next.getKey();
                String value = next.getValue();
                log.debug("key: {},value: {}", key, value);
            }
        }
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }

    @ApiOperation(value = "ip测试")
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

    /*@Resource
    private OpenFeignService openFeignService;*/
    // 会报错
    /*@Resource
    private OpenFeignService openFeignServices;*/

    /*@ApiOperation(value = "feign远程调用测试")
    @GetMapping(value = "/feign", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> openFeign() {
        String tomcatStatus = openFeignService.tomcatStatus();
        CommonResult<Map<String, Object>> mapCommonResult = JSONObject.parseObject(tomcatStatus,
                new TypeReference<CommonResult<Map<String, Object>>>() {
                });
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), mapCommonResult.getData());
    }*/

    @Resource
    private ForestClient forestClient;

    @ApiOperation(value = "forest测试")
    @GetMapping(value = "/forest", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> forestClient() {
        try {
            log.debug("forest request =====>");
            if (true) {
                throw new ApiException(ResultCode.VALIDATE_FAILURE);
            }
            CommonResult<Map<String, Object>> mapCommonResult = forestClient.helloForest();
//            String send1 = forestClient.send1("1", "2");
//            Map<String, Object> hashMap = Maps.newHashMap();
//            hashMap.put("a", "1");
//            hashMap.put("b", "2");
//            String send2 = forestClient.send2(hashMap);
            UserDO userDO = new UserDO();
            userDO.setUId("qwert");
            userDO.setName("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAotVuyODD3piClEgamOfr0iVQBj9rR+DTgTDigkD7hWHZzAccuSiQILzb9DcbQQwn0aTU/1k/1Pw2FA54LJorr34NW2tzKuYQSdAdE0+Ie");
            userDO.setId(123L);
            String send3 = forestClient.send3(userDO);
            return CommonResult.success(ResultCode.SUCCESS.getMessage(), mapCommonResult.getData());
        } catch (ForestNetworkException e) {
            log.error(e.getMessage(), e);
        }
        return CommonResult.failed();
    }

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @ApiOperation(value = "springEvent测试")
    @GetMapping(value = "/springEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> springEvent() {
        log.info("[placeOrder] start.");
        //消息
        PlaceOrderEventMessage eventMessage = new PlaceOrderEventMessage();
        eventMessage.setOrderId("123");
        //发布事件
        applicationEventPublisher.publishEvent(new PlaceOrderEvent(eventMessage));
        log.info("[placeOrder] end.");
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }
}
