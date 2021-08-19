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

import com.aircraft.codelab.core.entities.CommonResult;
import com.aircraft.codelab.core.enums.ResultCode;
import com.aircraft.codelab.labcore.pojo.entity.UserDO;
import com.aircraft.codelab.labcore.pojo.vo.UserVO;
import com.aircraft.codelab.labcore.service.UserConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    @ApiOperation(value = "redis测试", notes = "redis hash")
    @GetMapping("/redis")
    public CommonResult<Map<Object, Object>> helloRedis(@RequestParam(defaultValue = "0", name = "id") Long parentId) {
        log.debug("redis test");
        INFO.put("id", parentId);
        redisTemplate.opsForHash().putAll("test:map:2", INFO);
        Map<Object, Object> map = redisTemplate.opsForHash().entries("test:map:2");
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), map);
    }

    @ApiOperation(value = "mapstruct测试")
    @GetMapping("/mapstruct")
    public CommonResult<UserDO> helloMapstruct() {
        log.debug("mapstruct test");
        UserVO userVO = UserVO.builder().id(100L).name("zhang").build();
        UserDO userDO = UserConverter.INSTANCE.vo2do(userVO);
        UserDO build = userDO.toBuilder()
                .creatTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), build);
    }
}
