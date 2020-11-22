package com.aircraft.lab001.core.controller;

import com.aircraft.lab001.core.common.CommonResult;
import com.aircraft.lab001.core.enums.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @program: codelab
 * @description: test
 * @author: tao.zhang
 * @create: 2020-11-22 15:08
 **/
@RestController
@RequestMapping("/hello")
@Api(tags = "统一返回格式测试")
@Slf4j
public class TestController {
    private static final HashMap<String, Object> INFO;

    static {
        INFO = new HashMap<>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
    }

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation(value = "map测试", notes = "RequestBody接收Map参数")
    @GetMapping("/redis")
    public CommonResult<Map<Object, Object>> helloRedis(@RequestParam(defaultValue = "0", name = "id") Long parentId) {
        INFO.put("id", parentId);
        redisTemplate.opsForHash().putAll("test:map:2", INFO);
        Map<Object, Object> map = redisTemplate.opsForHash().entries("test:map:2");
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), map);
    }
}
