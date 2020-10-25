package com.zt_wmail500.demo.business.controller;

import com.zt_wmail500.demo.system.conf.ResponseResultBody;
import com.zt_wmail500.demo.system.util.Result;
import com.zt_wmail500.demo.system.util.ResultStatus;
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
@ResponseResultBody
public class JsonResultController {
    private static final HashMap<String, Object> INFO;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    static {
        INFO = new HashMap<>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
    }

    @GetMapping("/map")
    public Map<String, Object> hello() {
        return INFO;
    }

    @GetMapping("/result")
    public Result<Map<String, Object>> helloResult() {
        return Result.failure(ResultStatus.BAD_REQUEST);
    }

    @GetMapping("helloError")
    public HashMap<String, Object> helloError() throws Exception {
        throw new Exception("helloError");
    }

    @GetMapping("/parentId")
    public Result<Map<Object,Object>> helloRedis(@RequestParam(defaultValue="0",name ="id") Long parentId) {
        INFO.put("id",parentId);

        redisTemplate.opsForHash().putAll("test:map:2",INFO);
        Map<Object, Object> map = redisTemplate.opsForHash().entries("test:map:2");
        return Result.success(map);
    }

//    @GetMapping("helloMyError")
//    public HashMap<String, Object> helloMyError() throws Exception {
//        throw new ResultException();
//    }
}
