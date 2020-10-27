package com.zt_wmail500.demo.business.controller;

import com.zt_wmail500.demo.system.conf.APIException;
import com.zt_wmail500.demo.system.conf.ResultCode;
import com.zt_wmail500.demo.system.conf.ResultInfo;
import com.zt_wmail500.demo.system.conf.ResultInfoBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: demo
 * @description: 统一返回Json测试
 * @author: tao.zhang
 * @create: 2020-09-05 21:22
 **/
@RestController
@RequestMapping("/hello")
@Slf4j
public class JsonResultController {
    private static final HashMap<String, Object> INFO;

    static {
        INFO = new HashMap<>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
    }

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/map")
    public Map<String, Object> map() {
        return INFO;
    }

    @GetMapping("/test")
    public ResultInfo<?> test() {
        INFO.put("id", "100001");
        return ResultInfoBuilder.success();
    }

    @GetMapping("/result")
    public ResultInfo<?> Result() {
        log.info(">>>>>>>>>>>>>>>>>>>>");
        return ResultInfoBuilder.success(ResultCode.LOGIN_NO);
    }

    @GetMapping("/redis")
    public ResultInfo<Map<Object, Object>> helloRedis(@RequestParam(defaultValue = "0", name = "id") Long parentId) {
        INFO.put("id", parentId);
        redisTemplate.opsForHash().putAll("test:map:2", INFO);
        Map<Object, Object> map = redisTemplate.opsForHash().entries("test:map:2");
        return ResultInfoBuilder.success(ResultCode.SUCCESS, map);
    }

    @GetMapping("helloError")
    public HashMap<String, Object> helloError() {
        throw new APIException("helloError");
    }
}
