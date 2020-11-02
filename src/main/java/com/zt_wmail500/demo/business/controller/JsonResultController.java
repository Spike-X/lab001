package com.zt_wmail500.demo.business.controller;

import com.zt_wmail500.demo.business.pojo.User;
import com.zt_wmail500.demo.business.service.UserService;
import com.zt_wmail500.demo.system.conf.APIException;
import com.zt_wmail500.demo.system.conf.CommonResult;
import com.zt_wmail500.demo.system.conf.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
@Api(tags = "统一返回格式测试")
@Slf4j
@Validated
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
    @ApiOperation(value = "map测试",notes = "RequestBody接收Map参数")
    public CommonResult<Map<String, Object>> map(@RequestParam @Length(min = 3, max = 20) String key, @RequestParam @Length(min = 3, max = 20) String value) {
        INFO.put(key,value);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), INFO);
    }

    @GetMapping("/test")
    public CommonResult<?> test(@RequestParam @Length(min = 3, max = 20) String key) {
        log.info(key);
        INFO.put("id", "100001");
        return CommonResult.success();
    }

    @ApiOperation("result测试")
    @GetMapping("/result")
    public CommonResult<?> Result() {
        log.info(">>>>>>>>>>>>>>>>>>>>");
        return CommonResult.success(ResultCode.LOGIN_NO.getMessage());
    }

    @GetMapping("/redis")
    public CommonResult<Map<Object, Object>> helloRedis(@RequestParam(defaultValue = "0", name = "id") Long parentId) {
        INFO.put("id", parentId);
        redisTemplate.opsForHash().putAll("test:map:2", INFO);
        Map<Object, Object> map = redisTemplate.opsForHash().entries("test:map:2");
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), map);
    }

    @GetMapping("/helloError")
    public HashMap<String, Object> helloError() {
        throw new APIException("helloError");
    }

    @GetMapping("/apiError")
    public HashMap<String, Object> apiError() {
        throw new APIException(ResultCode.NO_PERMISSION);
    }

    @Autowired
    private UserService userService;

    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public CommonResult<String> addUser(@RequestBody @Valid User user) {
        log.info("1111111111");
        String s = userService.addUser(user);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), s);
    }
}
