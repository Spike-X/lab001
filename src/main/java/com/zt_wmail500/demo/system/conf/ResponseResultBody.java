package com.zt_wmail500.demo.system.conf;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * @program: demo
 * @description: @ResponseResultBody 注解
 * @author: tao.zhang
 * @create: 2020-09-06 09:00
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface ResponseResultBody {

}
